package com.example.ok.shipments.model;

/**
 * Created by OK on 2016/4/19.
 */
public class UpDataInfo {
    private String AppName;
    private String AppVersion;
    private String FileUrl;
    private String UpdateDescription;
    private String UpdateTime;
    private String Md5CheckSum;

    @Override
    public String toString() {
        return "UpDataInfo{" +
                "AppName='" + AppName + '\'' +
                ", AppVersion='" + AppVersion + '\'' +
                ", FileUrl='" + FileUrl + '\'' +
                ", UpdateDescription='" + UpdateDescription + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                ", Md5CheckSum='" + Md5CheckSum + '\'' +
                '}';
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getMd5CheckSum() {
        return Md5CheckSum;
    }

    public void setMd5CheckSum(String md5CheckSum) {
        Md5CheckSum = md5CheckSum;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getUpdateDescription() {
        return UpdateDescription;
    }

    public void setUpdateDescription(String updateDescription) {
        UpdateDescription = updateDescription;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }
}
