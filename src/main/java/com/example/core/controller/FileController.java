package com.example.core.controller;

import com.example.core.service.FtpService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.file.endpoint}")
public class FileController {
    @Autowired
    FtpService ftpService;
    @GetMapping("{folder}/{fileName}")
    @CrossOrigin
    public ResponseEntity<byte[]> getFile(@PathVariable String folder,@PathVariable String fileName) throws  IOException {
        byte[] fileContent = ftpService.retrieveFile(folder,fileName);
        String contentType = URLConnection.guessContentTypeFromName(fileName);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

    }



}
