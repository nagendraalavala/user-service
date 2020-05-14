package com.perficient.service.adaptor;

import org.mayCourse.CountryCurrency;
import org.mayCourse.CountryCurrencyResponse;
import org.mayCourse.TCountryInfo;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CountryServiceAdaptor extends WebServiceGatewaySupport
{
    private static final org.slf4j.Logger LOGGER =  LoggerFactory.getLogger(CountryServiceAdaptor.class);

    public String getCountryCurrencyDetails(String countryCode)
    {
        CountryCurrency countryCurrency = new CountryCurrency();
        countryCurrency.setSCountryISOCode(countryCode);

        CountryCurrencyResponse response = (CountryCurrencyResponse) getWebServiceTemplate().marshalSendAndReceive("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso",countryCurrency,
                new SoapActionCallback("http://www.oorsprong.org/websamples.countryinfo"));

        return response.getCountryCurrencyResult().getSName();
    }

}
