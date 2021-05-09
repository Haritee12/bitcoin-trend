package com.kuehnenagel.bitcointrend;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ApiCallsTest {

    @Autowired
    ApiCalls apiCalls;

    @BeforeEach
    public void setUp() {
        apiCalls = new ApiCalls();
    }

    @Test
    @DisplayName("Invalid Currency code detection test")
    public void testInvalidCurrencyCode() throws ParseException {
        assertFalse(apiCalls.validateEnteredCurrencyCode("ABC"));
    }

    @Test
    @DisplayName("Valid Currency code detection test")
    public void testValidCurrencyCode() throws ParseException {
        assertTrue(apiCalls.validateEnteredCurrencyCode("EUR"));
    }

    @Test
    @DisplayName("Test for Invalid Currency code entry in getBitcoinCurrentRate method")
    public void testInValidCurrencyCodeForCurrentRate() throws ParseException {
        String currencyCode = "universe";
        HttpResponse<String> currentPriceResponse = Unirest.get(ApiUtils.BITCOIN_CURRENT_PRICE_URL + currencyCode + ".json").asString();
        assertEquals(404, currentPriceResponse.getStatus());
        assertEquals("Sorry, your requested currency UNI is not supported or is invalid", currentPriceResponse.getBody());
    }

    @Test
    @DisplayName("Test for valid Currency code entry in getHistoricalMinMaxRate method")
    public void testValidCurrencyCodeForHistoricalRate() throws ParseException {
        JSONParser parser = new JSONParser();
        String currencyCode = "EUR";
        HttpResponse<String> historicalPriceResponse = Unirest.get(ApiUtils.BITCOIN_PRICE_HISTORY_URL)
                .queryString("start", "2021-05-01")
                .queryString("end", "2021-05-05")
                .queryString("currency", currencyCode)
                .asString();
        JSONObject json = (JSONObject) parser.parse(historicalPriceResponse.getBody());
        Map<String, Double> lastThirtyDaysBpiMap = (Map<String, Double>) json.get(ApiUtils.API_NAME);

        Double minValue = Collections.min(lastThirtyDaysBpiMap.values());
        Double maxValue = Collections.max(lastThirtyDaysBpiMap.values());
        assertEquals(minValue, 44324.0724);
        assertEquals(maxValue, 48116.6658);
    }

    @Test
    @DisplayName("Test for Invalid Currency code entry in getHistoricalMinMaxRate method")
    public void testInValidCurrencyCodeForHistoricalRate() throws ParseException {
        String currencyCode = "universe";
        HttpResponse<String> historicalPriceResponse = Unirest.get(ApiUtils.BITCOIN_PRICE_HISTORY_URL)
                .queryString("start", "2021-05-01")
                .queryString("end", "2021-05-05")
                .queryString("currency", currencyCode)
                .asString();
        assertEquals(404, historicalPriceResponse.getStatus());
        assertEquals("Sorry, that currency was not found", historicalPriceResponse.getBody());
    }

}
