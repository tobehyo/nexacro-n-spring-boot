package com.tobesoft.overseas.nexacrodemo.config;

import java.io.File;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.nexacro.java.xeni.services.GridExportImportServlet;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class NexacroXeniConfiguration {

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                // set nexacro-xeni context params
                String realPath = "";
                try {
                    realPath = new ClassPathResource("static").getFile().getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String exportPath = "file://" + realPath + File.separator + "export";
                String importPath = "file://" + realPath + File.separator + "import";

                // String exporURL = "http://localhost:8080/nexacrodemo/XExportImport";
                
                servletContext.setInitParameter("export-path", exportPath);
                // servletContext.setInitParameter("export-url", exporURL);
                servletContext.setInitParameter("import-path", importPath);                
                servletContext.setInitParameter("monitor-enabled", "true");
                servletContext.setInitParameter("monitor-cycle-time", "5");
                servletContext.setInitParameter("file-storage-time", "3");
                servletContext.setInitParameter("numFmt-lang", "ko");
            }
        };
    }
    
    @Bean
    public ServletRegistrationBean<Servlet> nexacroExcelExportImportServletRegistrationBean() {        
        return new ServletRegistrationBean<Servlet>(new GridExportImportServlet(), "/XExportImport", "/XImport");
    }   
}
