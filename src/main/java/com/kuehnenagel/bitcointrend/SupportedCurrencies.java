package com.kuehnenagel.bitcointrend;

import org.springframework.stereotype.Component;

@Component
public class SupportedCurrencies {
    String currency;
    String country;

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }
}
