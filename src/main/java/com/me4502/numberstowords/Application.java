package com.me4502.numberstowords;

import static spark.Spark.get;
import static spark.Spark.port;

import spark.ModelAndView;
import spark.template.jinjava.JinjavaEngine;

import java.util.HashMap;
import java.util.Map;

public class Application {

    private static String numbersToWords(String number) {
        try {
            // Remove any ',' or ' ' characters (International numeric separators)
            number = number.replace(",", "");
            number = number.replace(" ", "");

            NumberToWords converter = new NumberToWords(number);

            return "{\"response\": \"" + converter.convert() + "\"}";
        } catch(NumberFormatException e) {
            return "{\"response\": \"Not a valid number.\"}";
        } catch (IllegalArgumentException e) {
            return "{\"response\": \"" + e.getMessage() + "\"}";
        }
    }

    public static void main(String[] args) {
        port(8080);

        get("/", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "spark-template-jinjava");
            return new ModelAndView(attributes, "template/main.jin");
        }, new JinjavaEngine());

        get("/api/get_words/:number", (req, res) -> numbersToWords(req.params("number")));
    }
}
