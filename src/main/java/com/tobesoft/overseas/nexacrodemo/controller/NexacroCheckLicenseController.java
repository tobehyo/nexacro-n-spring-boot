package com.tobesoft.overseas.nexacrodemo.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// ctrl + shift + o
@RestController
public class NexacroCheckLicenseController {
	@GetMapping("/services/license")
    public void getNexacroLicenseInfo(HttpServletResponse response) throws IOException {
        OutputStream out = response.getOutputStream();
        new com.nexacro.java.xapi.util.JarInfo().info(out);
    }

}
