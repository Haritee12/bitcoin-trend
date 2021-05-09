package com.kuehnenagel.bitcointrend;


import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class BitcoinTrendApplication
        implements CommandLineRunner {

    @Autowired
    ApiCalls apiCalls;

    private static Logger LOG = LoggerFactory
            .getLogger(BitcoinTrendApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(BitcoinTrendApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws ParseException {

        LOG.info("EXECUTING : command line runner");

        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("WELCOME TO BITCOIN TREND");
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("Please enter currency code to see the trend :");

        Scanner in = new Scanner(System.in);
        String currencyCode = in.nextLine();

        System.out.println("----------------Showing Bitcoin rates in entered currency----------------");

        if (!apiCalls.validateEnteredCurrencyCode(currencyCode)) {
            System.out.println("Sorry, your requested currency " + currencyCode + " is not supported or is invalid");
            System.exit(0);
        }

        String currBitcoinRate = apiCalls.getBitcoinCurrentRate(currencyCode);
        System.out.println("The current Bitcoin rate : " + currBitcoinRate + " " + currencyCode);

        Map<String, Double> historicalBitcoinRates = apiCalls.getHistoricalMinMaxRate(currencyCode);
        double minRateForLastThirtyDays = historicalBitcoinRates.get("Min");
        double maxRateForLastThirtyDays = historicalBitcoinRates.get("Max");

        System.out.println("The lowest Bitcoin rate in the last 30 days : " + minRateForLastThirtyDays + " " + currencyCode);
        System.out.println("The highest Bitcoin rate in the last 30 days : " + maxRateForLastThirtyDays + " " + currencyCode);
        System.out.println("-------------------------------------------------------------------------------------------");
    }

}
