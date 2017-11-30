package com.myresource.codesky;


import com.myresource.BaseTaskJobs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 */
@Component
public class CodeskyTaskJobs extends BaseTaskJobs {

    private final static Logger logger = LoggerFactory.getLogger(CodeskyTaskJobs.class);
    private final static String baseurl ="http://www.codesky.net";

    private static final String home = "D:/resource";

    private final static String HOST ="www.codesky.net";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";

    @Scheduled(cron = "0 0 6 * * ? ")
    public void pullCodeskyResource(){
        Map<String,String> map = new LinkedHashMap<>(10);
        map.put("java","/java/");
        map.put("dotnet","/dotnet/");
        map.put("python","/python/");
        map.put("android","/android/");
        map.put("ios","/ios/");
        map.put("asp","/codedown/asp/");
        map.put("php","/codedown/php/");
        map.put("jsp","/codedown/jsp/");
        map.put("javascript","/codedown/javascript/");

        map.forEach((k,v)->{
            WebDriver webDriver = getPhantomJSDriver();
            webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            try {
                String url = baseurl + v;
                int page = pullFile(webDriver,url,k);
                for(int i=2;i<=page;i++){
                    pullFile(webDriver,url+"list-"+i+".htm",k);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                exitPhantom(webDriver);
            }
        });
    }


    public WebDriver getPhantomJSDriver() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
        desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "D:\\phantomjs-2.1.1\\bin\\phantomjs.exe");
        desiredCapabilities.setCapability("phantomjs.page.settings.userAgent", HOST);
        desiredCapabilities.setCapability("phantomjs.page.customHeaders.User-Agent", USER_AGENT);
        WebDriver webDriver = new PhantomJSDriver(desiredCapabilities);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return webDriver;
    }

    public void exitPhantom(WebDriver webDriver) {
        webDriver.close();
        webDriver.quit();
    }

    /**
     * 将InputStream写入本地文件
     * @param destination 写入本地目录
     * @param input 输入流
     * @throws IOException
     */
    private static void writeToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        input.close();
    }

    private int pullFile(WebDriver webDriver,String url,String k)throws Exception{
        int page = 0;
        String body = get(url);
        Document doc = Jsoup.parseBodyFragment(body);
        Elements dm2s = doc.getElementsByClass("dm2");
        page = Integer.valueOf(dm2s.last().child(0).text());
        Elements elements = doc.getElementsByTag("a");
        for(Element element : elements){
            String pageUrl = element.attr("href");
            if(pageUrl.contains("showhtml")){
                String fileName = element.text().trim();
                String destination = home + File.separator + k + File.separator + fileName +".rar";
                File localFile = new File(destination);
                //本地文件存在  则跳过
                if(localFile.exists()){
                    continue;
                }
                webDriver.get(baseurl+pageUrl);
                Thread.sleep(10*1000);
                Document pagedoc = Jsoup.parseBodyFragment(webDriver.getPageSource());
                Elements pageElements = pagedoc.getElementsByTag("a");
                for(Element e : pageElements){
                    String downloadUrl = e.attr("href");
                    if(downloadUrl.contains("download")){
                        Map<String,String> header = new HashMap<>();
                        header.put("Referer",baseurl+pageUrl);
                        InputStream is = getFile(baseurl+downloadUrl,header);
                        File file = new File(home + File.separator + k);
                        if(!file.exists()){
                            file.mkdir();
                        }

                        writeToLocal(destination,is);
                        Thread.sleep(15*1000);
                    }
                }
            }
        }
        return page;
    }

    public static void main(String[] args){
        CodeskyTaskJobs codeskyTaskJobs = new CodeskyTaskJobs();
        codeskyTaskJobs.pullCodeskyResource();
    }
}
