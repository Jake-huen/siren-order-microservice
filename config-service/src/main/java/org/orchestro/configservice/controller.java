package org.orchestro.configservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @Autowired
    private Environment env;

    @GetMapping("/hello")
    public String getEnvironmentProperties() {
        return env.getProperty("hello");
    }

    @GetMapping("/filePath")
    public String getFilePathProperties() {
        String filePath = env.getProperty("filePath");
        return filePath;
    }
}
