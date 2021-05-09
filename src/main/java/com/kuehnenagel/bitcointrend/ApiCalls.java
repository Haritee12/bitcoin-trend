package com.kuehnenagel.bitcointrend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiCalls {

    JSONParser parser = new JSONParser();
    Gson gson = new Gson();

    //validate user entered currency code
    public boolean validateEnteredCurrencyCode(String currencyCode) throws ParseException {
        HttpResponse<String> validateCurrencyCodeResponse = Unirest.get(ApiUtils.BPI_SUPPORTED_CURRENCY_URL).asString();
        JSONArray json = (JSONArray) parser.parse(validateCurrencyCodeResponse.getBody());
        TypeToken<List<SupportedCurrencies>> token = new TypeToken<List<SupportedCurrencies>>() {
        };
        List<SupportedCurrencies> supportedCurrenciesList = gson.fromJson(String.valueOf(json), token.getType());
        return supportedCurrenciesList.stream().anyMatch(o -> o.getCurrency().equals(currencyCode));
    }

    //getting current trends
    public String getBitcoinCurrentRate(String currencyCode) throws ParseException {

        HttpResponse<String> currentPriceResponse = Unirest.get(ApiUtils.BITCOIN_CURRENT_PRICE_URL + currencyCode + ".json").asString();
        if (200 != currentPriceResponse.getStatus()) {
            System.out.println(currentPriceResponse.getBody());
            System.exit(0);
        }
        JSONObject json = (JSONObject) parser.parse(currentPriceResponse.getBody());
        JSONObject currencyRate = (JSONObject) ((JSONObject) json.get(ApiUtils.API_NAME)).get(currencyCode);
        return (String) currencyRate.get("rate");
    }

    //getting historical trends
    public Map<String, Double> getHistoricalMinMaxRate(String currencyCode) throws ParseException {

        Map<String, Double> minAndMaxValues = new HashMap<>();
        HttpResponse<String> historicalPriceResponse = Unirest.get(ApiUtils.BITCOIN_PRICE_HISTORY_URL)
                .queryString("start", ApiUtils.DATE_BEFORE_THIRTY_DAYS.toString())
                .queryString("end", ApiUtils.CURRENT_DATE.toString())
                .queryString("currency", currencyCode)
                .asString();

        if (200 != historicalPriceResponse.getStatus()) {
            System.out.println(historicalPriceResponse.getBody());
            System.exit(0);
        }
        JSONObject json = (JSONObject) parser.parse(historicalPriceResponse.getBody());
        Map<String, Double> lastThirtyDaysBpiMap = (Map<String, Double>) json.get(ApiUtils.API_NAME);

        Double minValue = Collections.min(lastThirtyDaysBpiMap.values());
        Double maxValue = Collections.max(lastThirtyDaysBpiMap.values());

        minAndMaxValues.put("Min", minValue);
        minAndMaxValues.put("Max", maxValue);

        return minAndMaxValues;
    }

}
