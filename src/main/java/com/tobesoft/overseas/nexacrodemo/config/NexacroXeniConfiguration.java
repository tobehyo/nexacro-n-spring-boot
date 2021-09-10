package com.tobesoft.overseas.nexacrodemo.config;

import java.io.File;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.nexacro.java.xeni.services.GridExportImportServlet;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NexacroXeniConfiguration {

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                // set nexacro-xeni context params
                String realPath = new File("src/main/resources/static").getAbsolutePath();
                String exportPath = "file://" + realPath + "\\export";
                String importPath = "file://" + realPath + "\\import";

                servletContext.setInitParameter("export-path", exportPath);
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
