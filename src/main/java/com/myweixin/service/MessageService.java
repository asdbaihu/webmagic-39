package com.myweixin.service;

import com.dao.MessageMapper;
import com.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageDao;

    public int add(Message message){
        return messageDao.insert(message);
    }
}
