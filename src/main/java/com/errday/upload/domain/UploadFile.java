package com.errday.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String UploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        UploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
