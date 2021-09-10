package com.tobesoft.overseas.nexacrodemo.controller;

import java.io.IOException;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadStaticFile {

    private static final Logger log = LoggerFactory.getLogger(DownloadStaticFile.class);

    private final ResourceLoader resourceLoader;

    public DownloadStaticFile(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    @PostMapping("/download/{fileName:.*}")
    public ResponseEntity<Resource> resouceFileDownload(@PathVariable String fileName) throws IOException {
        
        log.debug("fileName {} ", fileName);
        Resource resource = resourceLoader.getResource("classpath:static/"+ fileName);

        String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());

        if (mimeType == null) {	
            mimeType = "application/octet-stream";
        }        

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        header.add(HttpHeaders.CONTENT_LENGTH, resource.contentLength() + "");
        header.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    // @PostMapping("/download/{fileName:.*}")
    // public void resouceFileDownload(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        
    //     log.debug("fileName {} ", fileName);
    //     Resource resource = resourceLoader.getResource("classpath:static/"+ fileName);

    //     if(resource.exists()) {
			
    //         String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());

	// 		if (mimeType == null) {	
	// 			mimeType = "application/octet-stream";
	// 		}
    //         System.out.println("file name::: " + fileName + "  mimeType::: " + mimeType);
	// 		response.setContentType(mimeType);
    //         response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + resource.getFilename()) + "\"");            
    //         response.setContentLength((int) resource.contentLength());

	// 		InputStream inputStream = resource.getInputStream();
	// 		FileCopyUtils.copy(inputStream, response.getOutputStream());
            
    //     }
    //     else {
    //         return;
    //     }

    // }       
}
