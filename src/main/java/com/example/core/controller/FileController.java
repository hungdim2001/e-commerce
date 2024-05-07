package com.example.core.controller;

import com.example.core.service.FtpService;
import com.example.core.util.FtpClientTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private FtpService ftpService;
    @Value("${api.file.endpoint}")
    private String endpoint;

    @Autowired
    private FtpClientTemplate ftpTemplate;
    @RequestMapping(value = "**", method = RequestMethod.GET)
    @CrossOrigin
    public ResponseEntity<byte[]> getFile(HttpServletRequest request) throws Exception {

        String folderPath  = request.getRequestURL().toString().split(endpoint)[1];
        String[] pathSegments = folderPath.split("/");
        String fileName = pathSegments[pathSegments.length - 1]; // Last segment is the file name
        String folder = folderPath.substring(0, folderPath.length() - fileName.length() - 1); // Extracting folder path
        byte[] fileContent = ftpService.retrieveFile(folder,fileName);
        String contentType = URLConnection.guessContentTypeFromName(fileName);
        if (contentType == null) {
            contentType = "image/svg+xml";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

//    @RequestMapping(value = "**", method = RequestMethod.GET)
//    @CrossOrigin
//    public ResponseEntity<byte[]> getFilesth(HttpServletRequest request) throws Exception {
//
//        String folderPath  = request.getRequestURL().toString().split(endpoint)[1];
//        String[] pathSegments = folderPath.split("/");
//        String fileName = pathSegments[pathSegments.length - 1]; // Last segment is the file name
//        String folder = folderPath.substring(0, folderPath.length() - fileName.length() - 1); // Extracting folder path
//        byte[] fileContent = ftpTemplate.downloadFile(folder, fileName);
//        String contentType = URLConnection.guessContentTypeFromName(fileName);
//        if (contentType == null) {
//            contentType = "application/octet-stream";
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(contentType));
//        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());
//        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//    }

}
