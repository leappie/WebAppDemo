package com.example.webappdemo.data;

import com.example.webappdemo.util.ParseString;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
Runnable is a thread interface meant to get the data in the background
 */
public class GetData implements Runnable {

    //vars
    private volatile String API_KEY; // volatile to make sure the value that is shared between the threads is the same
    private volatile String jsonString;
    private volatile String location;
    private volatile String date;

    /**
     * Constructor to initialize global variables. These values are passed from HelloServlet through ParseJSON.
     *
     * @param API_KEY API key constant
     * @param location location from input
     * @param date date from input
     */
    public GetData(String API_KEY, String location, String date) {
        this.API_KEY = API_KEY;
        this.location = location;
        this.date = date;
    }

    /**
     * Runnable interface method used in new Thread(...).start();
     *
     * In this method the string from getData() is set to the variable jsonString,
     * which can be retrieved through a getter.
     */
    @Override
    public void run() {
        try {
            this.jsonString = getData();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the actual methods that gets te JSON format String from the url
     *
     * @return return data as String
     * @throws IOException
     * @throws InterruptedException
     */
    private String getData() throws IOException, InterruptedException {
        String apiUrl;

        /*
        Get individual values from variable date.
        Here I make use of a static method that returns an array and get the items from the array and assign them to
        variables.
         */
        String day = ParseString.getIndividualValues(date)[0];
        String month = ParseString.getIndividualValues(date)[1];
        String year = ParseString.getIndividualValues(date)[2];

        /*
        The api url will be set based on input, if the input is faulty this will return a responseCode != 200
        However year cannot be null. Will I used an if statement in HelloServlet to make sure this does not happen,
        I also use here a default value. This is because the parseInt will throw a nullpointer exception.
         */
        if (day == null && month != null && year != null) { // if input is mm-yyyy then use the following method
            apiUrl = setAPIUrl(location, Integer.parseInt(year), Integer.parseInt(month)); // set url based on input

        } else if (day == null && month == null && year != null) { // if input is yyyy
            apiUrl = setAPIUrl(location, Integer.parseInt(year));

        } else if (year == null) { // error case we need as default a year
            apiUrl = setAPIUrl(location, 0);

        } else { // if input is dd-mm-yyyy
            apiUrl = setAPIUrl(location, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        }

        // make get request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiUrl))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //request response

        return response.body();
    }

    /**
     * Multiple setAPIUrl() methods based on input.
     *
     * @param country
     * @param year
     * @param month
     * @param day
     * @return String, return API URL based on the input above
     */
    private String setAPIUrl(String country, int year, int month, int day) {

        String apiUrl = "https://holidayapi.com/v1/holidays?pretty"
                + "&key=" + API_KEY
                + "&country=" + country
                + "&year=" + year
                + "&day=" + day
                + "&month=" + month;

        return apiUrl;
    }

    private String setAPIUrl(String country, int year, int month) {

        String apiUrl = "https://holidayapi.com/v1/holidays?pretty"
                + "&key=" + API_KEY
                + "&country=" + country
                + "&year=" + year
                + "&month=" + month;

        return apiUrl;
    }


    private String setAPIUrl(String country, int year) {

        String apiUrl = "https://holidayapi.com/v1/holidays?pretty"
                + "&key=" + API_KEY
                + "&country=" + country
                + "&year=" + year;

        return apiUrl;
    }

    /**
     * Returns the variable which was set in the run() method
     * @return String
     */
    public String getJsonString() {
        return jsonString;
    }
}
