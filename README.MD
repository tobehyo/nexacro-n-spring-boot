# Use Nexacro N XAPI in Spring Boot
> ###### 이 저장소의 데모 샘플은 투비소프트 중국 법인에서 활용할 목적으로 [Nexacro N Demo](https://github.com/tobehyo/nexacro-n-demo) 프로젝트의 파일 다운로드 및 업로드, 대용량 조회, 엑셀 Export 및 Import에 대한 서비스 로직을 Spring Boot 이용하여 간단하게 구현한 샘플입니다.

## Getting started
- Eclipse, IntelliJ IDEA or Visual Studio Code 등 익숙한 JAVA 개발 도구를 다운로드 후 설치 합니다.
- 이 저장소를 복제(Clone) 혹은 [다운로드](https://github.com/tobehyo/nexacro-n-spring-boot/archive/refs/heads/main.zip) 합니다.  
   ``` bash
   git clone https://github.com/tobehyo/nexacro-n-demo.git
   ```
   *다운로드한 파일은 압축해제 합니다.*
- 새롭게 설치한 혹은 사용하고 있는 자바 개발툴을 이용하여 프로젝트를 오픈합니다.

## Sample Information
- `MyCorsConfiguration.java` Nexacro를 이용하여 서비스 URL를 호출 시 Cross domain 오류를 대응하기 위한 Cors 설정 Class 입니다.
- `NexacroXeniConfiguration.java` Spring boot에서 Nexacro XENI를 사용하기 위한 설정 Class 입니다.
> 엑셀 export 시 사용하는 url은 export-url 파라미터를 이용하여 변경할 수 있습니다.  
> Default URL: `[host]/XExportImport`
``` java
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
```
- `NexacroCheckLicenseController.java` 라이선스를 확인하는 Class 입니다.
- `NexacroApiSampleController.java` 기본적인 XAPI 호출를 테스트한 Class 입니다.
- `DownloadStaticFile.java` Nexacro 에서 FileDownTransfer를 이용하여 다운로드 시 POST 방식을 테스트한 Class 입니다.
- `NexacroFileUploadDownloadControl.java` 파일 다운로드 및 업로드 테스트한 Class 입니다.
- `NexacroLargeDataController.java` 대용량 조회를 테스트한 Class 입니다. DB 연결은 배제하고 local 파일을 읽어 테스트하였습니다.
- `NexacroPivotDataController.java` Pivot Grid에 사용되는 데이터를 조회하는 Class입니다.

## Download Nexacro N Demo Project
[Download Nexacro N Demo](https://github.com/tobehyo/nexacro-n-demo/archive/refs/heads/main.zip)
