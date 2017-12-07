package com.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author admin
 */
public class Codesky {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codeskyId;
    private int status;
    private String type;
    private String pageUrl;
    private String downloadUrl;
    private String fileName;

    public long getCodeskyId() {
        return codeskyId;
    }

    public void setCodeskyId(long codeskyId) {
        this.codeskyId = codeskyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
