package com.myresource.codesky;


import com.common.BaseTaskJobs;
import com.domain.Codesky;
import com.domain.bo.CodeskyBo;
import com.myresource.service.CodeskyService;
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

import javax.annotation.Resource;
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



    @Resource
    CodeskyService codeskyService;

//    @Scheduled(cron = "0 56 14 * * ? ")
    public void pullCodeskyResource(){
        Map<String,String> map = new LinkedHashMap<>(10);
//        map.put("java","/java/");
//        map.put("dotnet","/dotnet/");
//        map.put("python","/python/");
//        map.put("android","/android/");
//        map.put("ios","/ios/");
        map.put("asp","/codedown/asp/");
        map.put("php","/codedown/php/");
        map.put("jsp","/codedown/jsp/");
        map.put("javascript","/codedown/javascript/");

        map.forEach((k,v)->{
            new Thread(()-> pull(k,v)).start();
        });
    }

    private void pull(String k,String v){
        WebDriver webDriver = getPhantomJSDriver();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        try {
            String url = baseurl + v;
            int page = pullFile(webDriver,url,k);
            for(int i=2;i<=page;i++){
                pullFile(webDriver,url+"list-"+i+".htm",k);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }finally {
            exitPhantom(webDriver);
        }
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
            if(pageUrl.contains("showhtml")||pageUrl.contains("codedown/html")){
                String fileName = element.text().trim().replaceAll("[/\\\\:*?<>|]","");
                webDriver.get(baseurl+pageUrl);
                Thread.sleep(5*1000);
                Document pagedoc = Jsoup.parseBodyFragment(webDriver.getPageSource());
                Elements pageElements = pagedoc.getElementsByTag("a");
                for(Element e : pageElements){
                    String downloadUrl = e.attr("href");
                    if(downloadUrl.contains("download")||(downloadUrl.contains("webdown")&&downloadUrl.contains("no=1"))){
                        Codesky codesky = new Codesky();
                        codesky.setType(k);
                        codesky.setFileName(fileName);
                        codesky.setPageUrl(pageUrl);
                        codesky.setDownloadUrl(downloadUrl);
                        codesky.setStatus(1);
                        codeskyService.addCodesky(codesky);
                    }
                }
            }
        }
        return page;
    }

//    @Scheduled(cron = "0 07 17 * * ? ")
    public void downloadUrl(){
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
        CodeskyBo bo = new CodeskyBo();
        bo.setStatus(1);
        Map<String,String> ipMap = new HashMap<>();
        WebDriver webDriver = getPhantomJSDriver();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            for(int i=1;i<=1;i++){
                try {
                    String proxyUrl = "http://www.xicidaili.com/wt/"+i;
                    webDriver.get(proxyUrl);
                    Thread.sleep(5*1000);
                    String response = webDriver.getPageSource();
                    Document doc = Jsoup.parseBodyFragment(response);
                    Element list = doc.getElementById("ip_list");
                    Elements trs = list.getElementsByTag("tr");
                    trs.remove(0);
                    for(Element e : trs){
                        String host = e.child(1).text().trim();
                        String prot = e.child(2).text().trim();
                        if(testProxy(baseurl,host,Integer.valueOf(prot))){
                            ipMap.put(host,prot);
                        }
                    }
                }catch (Exception e){
                    logger.debug(e.getMessage());
                }
            }


        map.forEach((k,v)->{
            bo.setType(k);
            List<Codesky> codeskies = codeskyService.getCodesky(bo);
            if(ipMap!=null&&ipMap.size()>0){
                downloadFile(codeskies,ipMap);
            }
        });

    }


    public void downloadFile(List<Codesky> codeskies,Map<String,String> map) {

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map.Entry<String, String> entry = it.next();
        String host = entry.getKey();
        int port = Integer.valueOf(entry.getValue());
        for (Codesky codesky : codeskies) {
            String destination = home + File.separator + codesky.getType() + File.separator + codesky.getFileName() + ".rar";
            logger.debug("开始下载文件:"+destination);
            File localFile = new File(destination);
            //本地文件存在  则跳过
            if (localFile.exists()) {
                codesky.setStatus(2);
                codeskyService.updateCodesky(codesky);
                continue;
            }
            Map<String, String> header = new HashMap<>();
            header.put("Referer", baseurl + codesky.getPageUrl());
            try {
                InputStream is = getFile(baseurl + codesky.getDownloadUrl(), header,host,port);
                File file = new File(home + File.separator + codesky.getType());
                if (!file.exists()) {
                    file.mkdir();
                }
                writeToLocal(destination, is);
                if(txt2String(localFile).contains("我们检测到您下载过于频繁，现已封闭您的IP二小时")){
                    localFile.delete();
                    if(it.hasNext()){
                        entry = it.next();
                        host = entry.getKey();
                        port = Integer.valueOf(entry.getValue());
                        map.remove(host);
                    }
                }else{
                    codesky.setStatus(2);
                    codeskyService.updateCodesky(codesky);
                }
            } catch (Exception e) {
                logger.debug(e.getMessage());
                continue;
            }
        }
    }


    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            //构造一个BufferedReader类来读取文件
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s = null;
            //使用readLine方法，一次读一行
            while((s = br.readLine())!=null){
                result.append(System.lineSeparator()+s);
            }
            fr.close();
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

}
