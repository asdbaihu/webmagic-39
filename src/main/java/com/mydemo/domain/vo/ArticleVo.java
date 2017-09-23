package com.mydemo.domain.vo;

import com.mydemo.domain.Article;

public class ArticleVo extends Article {

    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
