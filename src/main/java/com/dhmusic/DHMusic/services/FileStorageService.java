package com.dhmusic.DHMusic.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${fileRepositoryFolder}")
    private String folderName;

    private Logger log = LoggerFactory.getLogger(FileStorageService.class);

    private String getFileRepositoryFolder(){
        return System.getProperty("user.home")+"/OneDrive/Desktop/" +folderName;
    }

    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        // assignign a random name to the file so it is unique
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;

        log.debug("Saving new file with name " + newFileName + " and extension " + extension);
        log.debug("Complete file name: " + completeFileName);

        log.debug("Saving to location: " + getFileRepositoryFolder());

        File finalFolder = new File(getFileRepositoryFolder());
        if(!finalFolder.exists()) throw new IOException("Final folder does not exists");
        if(!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");

        File finalDestination = new File(getFileRepositoryFolder() + File.separator + completeFileName);
        if(finalDestination.exists()) throw new IOException("File conflict");
        log.debug("File correctly saved");
        file.transferTo(finalDestination);
        return completeFileName;
    }

    public byte[] download(String filename) throws IOException {
        File fileFromRepository = new File(getFileRepositoryFolder() + File.separator + filename);
        if(!fileFromRepository.exists()) throw new IOException("File " + filename + " does not exists");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }


}
