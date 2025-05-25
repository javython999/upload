package com.errday.upload.file;

import com.errday.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadFiles.add(storeFile(multipartFile));
            }
        }
        return uploadFiles;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFileName, storeFileName);

    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos + 1);
    }


}
