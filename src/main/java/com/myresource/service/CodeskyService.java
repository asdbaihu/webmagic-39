package com.myresource.service;

import com.dao.CodeskyMapper;
import com.domain.Codesky;
import com.domain.bo.CodeskyBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 */
@Service
public class CodeskyService {

    @Autowired
    private CodeskyMapper codeskyMapper;

    public List<Codesky> getCodesky(CodeskyBo bo){
        return codeskyMapper.getCodesky(bo);
    }

    public void addCodesky(Codesky codesky) {
        codeskyMapper.insert(codesky);
    }

    public void updateCodesky(Codesky codesky){
        codeskyMapper.updateByPrimaryKey(codesky);
    }
}
