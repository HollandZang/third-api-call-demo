package com.holland.demo;

import com.holland.demo.WSXxgkVin.XSXxgkVinServiceHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public XSXxgkVinServiceHelper xsXxgkVinServiceHelper(
            @Value("${WSXxgkVin.manufid}") String manufid,
            @Value("${WSXxgkVin.password}") String password
    ) {
        return new XSXxgkVinServiceHelper(manufid, password);
    }
}
