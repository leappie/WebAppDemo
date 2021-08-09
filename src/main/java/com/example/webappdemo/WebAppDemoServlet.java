package com.example.webappdemo;

import java.io.*;

import com.example.webappdemo.util.ParseJSON;
import com.example.webappdemo.util.ParseString;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * @WebServlet(name = "HelloServlet", urlPatterns = {"/", ""})
 * Used to set the directory to this servlet
 */
@WebServlet(name = "HelloServlet", urlPatterns = {"/", ""})
public class WebAppDemoServlet extends HttpServlet {

    private static volatile String API_KEY = "!!!!!!!!!!!!!!!!API KEY HERE!!!!!!!!!!!!!!";

    /**
     * Handles the get request
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // logging
        System.out.println("GET request called");

        //Forward to index.jsp
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

    }

    /**
     * Because we post location and date, this method is called to get the location and date
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // logging
        System.out.println("POST request called");

        //Parameters retrieved from the post request
        String location = request.getParameter("location");
        String date = request.getParameter("date");

        String result = null;

        /*
        Validate the input
        input types:
        dd-mm-yyyy
        mm-yyyy
        yyyy

        Also check for invalid charachters that might cause it to crash
         */
        if (location == null || location.isEmpty()) {
            result = "No location was given.";
        } else {
            if (ParseString.checkForInvalidCharacters(location)) {
                result = "Invalid characters. Check input.";
            } else {
                if (date == null || date.isEmpty()) {
                    result = "No date was given.";
                } else {
                    if (ParseString.checkForInvalidCharacters(date)) {
                        result = "Invalid characters. Check input.";
                    } else {
                        /*
                        Note: we can also check here for a minus sign for the dates and that the year is
                        in between 1 and 2050+
                         */

                        //get holidays
                        ParseJSON parseJSON = new ParseJSON(API_KEY, location, date);

                        try {
                            result = parseJSON.getResult();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

        request.setAttribute("location", location);
        request.setAttribute("date", date);
        request.setAttribute("result", result);

        //Forward to jsp page
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

    }


}