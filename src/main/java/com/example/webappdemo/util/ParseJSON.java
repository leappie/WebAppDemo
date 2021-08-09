package com.example.webappdemo.util;

import com.example.webappdemo.data.GetData;
import org.json.JSONArray;
import org.json.JSONObject;

/*
Parse the json data to get the holiday name and date
 */
public class ParseJSON {

    // var needed to call the getData method
    private final GetData getData;
    private String location;

    /**
     * Constructor to pass through the relevant information through here to getData
     *
     * @param apiKey
     * @param location
     * @param date
     */
    public ParseJSON(String apiKey, String location, String date) {
        this.getData = new GetData(apiKey, location, date);
        this.location = location;
    }

    /**
     * Here the JSON string will be parsed to get the holiday name and date.
     *
     * @param responseBody is the body retrieved from the getData() method in the GetData class.
     * @return returns the parsed result from the JSON String that was passed through it.
     */
    private String parseJSON(String responseBody) {
        StringBuilder result = new StringBuilder("");
        JSONObject jsonObjects = new JSONObject(responseBody);
        String responseCode = jsonObjects.get("status").toString();

        /*
        Error, status is not 200
         */
        if (responseCode.equals("400")) {
            result.append("The requested country ")
                    .append(location)
                    .append(" is invalid")
                    .append("\n")
                    .append("Check inputs. Location can be only of the following format: UK, NL, US etc.");
        } else if (responseCode.equals("402")) {
            result.append("Free accounts are limited to last year's historical data only. Upgrade to premium for access to all holiday data");
        } else if (!responseCode.equals("200")) {
            result.append("Unknown error. Visit https:\\/\\/holidayapi.com\\/docs");
        } else {
            JSONArray holidaysArray = jsonObjects.getJSONArray("holidays");

            try {
            /*
            In case the input date resulted in no holiday
             */
                if (holidaysArray.isEmpty()) {
                    result.append("Nothing found");
                } else {
                /*
                Else there must be holidays
                 */
                    for (int i = 0; i < holidaysArray.length(); i++) {
                        JSONObject jsonObject = holidaysArray.getJSONObject(i);

                    /*
                    If the jsonObject is null, we have reached the end of the array. So break the loop.
                    And return the result
                     */
                        if (jsonObject == null) {
                            break;
                        }
                        String holidayName = jsonObject.getString("name");
                        String holidayDate = jsonObject.getString("date");

                        result.append(holidayName).append(", ").append(holidayDate).append("\n"); // append result with newline after each result
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * The getResult() method executes the getData.run() in the background.
     * Hereafter the getData.getJsonString() method will be passed through the parseJSON() method
     * to get the holiday name and date.
     *
     * @return returns multiple line string
     * @throws InterruptedException
     */
    public String getResult() throws InterruptedException {
        Thread thread = new Thread(getData);
        thread.start();
        thread.join();

        return parseJSON(getData.getJsonString());
    }
}
