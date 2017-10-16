package com.myweixin.controller;


import com.alibaba.fastjson.JSON;
import com.common.BaseController;
import com.common.CheckoutUtil;
import com.domain.Message;
import com.myweixin.common.TulingService;
import com.myweixin.service.MessageService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@Controller
@EnableAutoConfiguration
@RequestMapping("/access")
public class AccessController extends BaseController{

    @Resource
    MessageService messageService;
    @Resource
    TulingService tulingService;

    @RequestMapping(path="/hello",method= RequestMethod.GET)
    public void hello( HttpServletRequest request, HttpServletResponse response){
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
            try {
                PrintWriter print = response.getWriter();
                print.write(echostr);
                print.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(path="/hello",method= RequestMethod.POST)
    public void weChat( HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletInputStream in = request.getInputStream();
            SAXReader saxReadr = new SAXReader();
            Document doc = saxReadr.read(in);
            Element root = doc.getRootElement();

            String msgType = root.element("MsgType").getTextTrim();
            // 根据消息类型获取对应的消息内容
            switch (msgType){
                case "text":
                    Message message = new Message();
                    message.setFromUserName(root.element("FromUserName").getTextTrim());
                    message.setToUserName(root.element("ToUserName").getTextTrim());
                    message.setCreateTime(new Date(Long.valueOf(root.element("CreateTime").getTextTrim())));
                    message.setContent(root.element("Content").getTextTrim());
                    message.setMsgType(msgType);
                    message.setMsgId(root.element("MsgId").getTextTrim());
                    messageService.add(message);

                    Message sendMsg = new Message();
                    sendMsg.setFromUserName(message.getToUserName());
                    sendMsg.setToUserName(message.getFromUserName());
                    sendMsg.setCreateTime(new Date());

                    String res = tulingService.postMessage(message.getContent());
                    Map<String,String> dataMap = (Map<String,String>) JSON.parse(res);
                    sendMsg.setContent(dataMap.get("info"));
                    sendMsg.setMsgType(msgType);
                    messageService.add(sendMsg);

                    StringBuffer str = new StringBuffer();
                    str.append("<xml>");
                    str.append("<ToUserName><![CDATA[" + sendMsg.getToUserName() + "]]></ToUserName>");
                    str.append("<FromUserName><![CDATA[" + sendMsg.getFromUserName() + "]]></FromUserName>");
                    str.append("<CreateTime>" + new Date().getTime() + "</CreateTime>");
                    str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");
                    str.append("<Content><![CDATA[" + sendMsg.getContent() +"]]></Content>");
                    str.append("</xml>");
                    response.getWriter().write(str.toString());
                    break;
                case "image":
//                    System.out.println("获取多媒体信息");
//                    System.out.println("多媒体文件id：" + inputMsg.getMediaId());
//                    System.out.println("图片链接：" + inputMsg.getPicUrl());
//                    System.out.println("消息id，64位整型：" + inputMsg.getMsgId());
//                    OutputMessage outputMsg = new OutputMessage();
//                    outputMsg.setFromUserName(servername);
//                    outputMsg.setToUserName(custermname);
//                    outputMsg.setCreateTime(returnTime);
//                    outputMsg.setMsgType(msgType);
//                    ImageMessage images = new ImageMessage();
//                    images.setMediaId(inputMsg.getMediaId());
//                    outputMsg.setImage(images);
//                    System.out.println("xml转换：/n" + xs.toXML(outputMsg));
//                    response.getWriter().write(xs.toXML(outputMsg));
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
