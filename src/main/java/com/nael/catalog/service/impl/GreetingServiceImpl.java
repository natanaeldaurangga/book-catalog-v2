package com.nael.catalog.service.impl;

import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.nael.catalog.config.ApplicationProperties;
import com.nael.catalog.config.CloudProperties;
import com.nael.catalog.service.GreetingService;

@Service // Memberi tahu spring container bahwa ini adalah komponen yang harus di manage
         // di spring container
public class GreetingServiceImpl implements GreetingService {

    // @Value("${welcome.text}") // dibinding dengan variable welcome.text di file
    // aplication.properties
    // private String welcomeText;// kita akan binding/ikat dengan salah satu
    // variabel di file properties

    // @Value("${timezone}")
    // private String timeZone;

    // @Value("${currency}")
    // private String currency;

    private ApplicationProperties appProperties;

    private CloudProperties cloudProperties;

    // dibuat constructornya supaya diinjeksikan
    public GreetingServiceImpl(ApplicationProperties appProperties, CloudProperties cloudProperties) {
        this.appProperties = appProperties;
        this.cloudProperties = cloudProperties;
    }

    @Override
    public String sayGreeting() {
        // TODO Auto-generated method stub
        System.out.println(cloudProperties.getApiKey());
        TimeZone tzIndo = TimeZone.getTimeZone(appProperties.getTimezone());

        return appProperties.getWelcomeText() + ", our time zone : " + tzIndo.getDisplayName() +
                ", our currency: " + appProperties.getCurrency();
    }

    // TODO: Lanjut ke cara membaca property pada environment

}
