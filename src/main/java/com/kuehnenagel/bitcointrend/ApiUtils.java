package com.kuehnenagel.bitcointrend;

import java.time.LocalDate;

public class ApiUtils {

    public static final String BITCOIN_CURRENT_PRICE_URL = "https://api.coindesk.com/v1/bpi/currentprice/";
    public static final String BITCOIN_PRICE_HISTORY_URL = "https://api.coindesk.com/v1/bpi/historical/close.json";
    public static final String BPI_SUPPORTED_CURRENCY_URL = "https://api.coindesk.com/v1/bpi/supported-currencies.json";
    public static final String API_NAME = "bpi";
    public static LocalDate CURRENT_DATE = LocalDate.now();
    public static LocalDate DATE_BEFORE_THIRTY_DAYS = CURRENT_DATE.minusDays(30);
}
