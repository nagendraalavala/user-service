package com.perficient.service.adaptor;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.perficient.service.config.ConfigProperties;
import com.perficient.service.types.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoateAdaptor
{
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoateAdaptor.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConfigProperties config;

    public QuoateAdaptor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getStaticQuote")
    public String getQuote() {

        Quote quote = restTemplate.getForObject(config.getEndpoint(), Quote.class);
        LOGGER.info(quote.toString());

        return quote.getValue().getQuote();
    }

    public String getStaticQuote() {
        return "This is coming from the static quote";
    }
}
