package com.myblog.service;

import com.dao.CategoryMapper;
import com.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryDao;

    public List<Category> getAll(){
        return categoryDao.selectAll();
    }
}
