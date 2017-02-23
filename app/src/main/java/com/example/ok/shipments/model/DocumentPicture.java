package com.example.ok.shipments.model;

/**
 * Created by ${lida} on 2016/5/25.
 */
public class DocumentPicture  {

    private String RecordId;
    private String DocumentId;
    private String IamgeData="";
    private String IsSignUpPicture;
    private String Uploader;
    private String UploadTime;
    private String Description;

    public String getRecordId() {
        return RecordId;
    }

    public void setRecordId(String recordId) {
        RecordId = recordId;
    }

    public String getUploadTime() {
        return UploadTime;
    }

    public void setUploadTime(String uploadTime) {
        UploadTime = uploadTime;
    }

    public String getUploader() {
        return Uploader;
    }

    public void setUploader(String uploader) {
        Uploader = uploader;
    }

    public String getIsSignUpPicture() {
        return IsSignUpPicture;
    }

    public void setIsSignUpPicture(String isSignUpPicture) {
        IsSignUpPicture = isSignUpPicture;
    }

    public String getIamgeData() {
        return IamgeData;
    }

    public void setIamgeData(String iamgeData) {
        IamgeData += iamgeData;
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "DocumentPicture{" +
                "RecordId='" + RecordId + '\'' +
                ", DocumentId='" + DocumentId + '\'' +
                ", IamgeData='" + IamgeData + '\'' +
                ", IsSignUpPicture='" + IsSignUpPicture + '\'' +
                ", Uploader='" + Uploader + '\'' +
                ", UploadTime='" + UploadTime + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
