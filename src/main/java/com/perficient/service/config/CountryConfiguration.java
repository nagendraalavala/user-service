package com.perficient.service.config;

import com.perficient.service.adaptor.CountryServiceAdaptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CountryConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("org.oorsprong.webservices");
        return marshaller;
    }

    @Bean
    public CountryServiceAdaptor countryServiceAdaptor(Jaxb2Marshaller marshaller) {
        CountryServiceAdaptor client = new CountryServiceAdaptor();
        client.setDefaultUri("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}