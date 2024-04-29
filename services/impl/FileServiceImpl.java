package com.blogappapi.services.impl;

import com.blogappapi.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String fileName=file.getOriginalFilename();
        String randomId= UUID.randomUUID().toString();
        String fileName1=randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
        String fullPath=path+ File.separator+fileName1;

        //create folder if it's not created
        File fileFolder=new File(path);
        if(!fileFolder.exists()){
            fileFolder.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        return fileName1;
    }

    @Override
    public InputStream getResourse(String path, String filename) throws FileNotFoundException {
        String fullPath=path+File.separator+filename;
        InputStream is=new FileInputStream(fullPath);
        return is;
    }
}
