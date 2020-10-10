package com.daviddemartini.servlet.burnban.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.daviddemartini.servlet.burnban.Models.CCFD;
import com.daviddemartini.servlet.burnban.co.comal.tx.us.FireMarshall;
import java.io.IOException;

public abstract class RequestHandler implements HttpHandler {

    private static boolean debug = false;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if("GET".equals(httpExchange.getRequestMethod())) {
            CCFD status = handleGetRequest(httpExchange);
            handleResponse(httpExchange,status);
        }
    }

    /**
     * Get the USGS Data for requested sensor
     * @param httpExchange
     * @return
     */
    private CCFD handleGetRequest(HttpExchange httpExchange) {
        String htmlContent;

        try {
            System.out.println("Request Fire Marshall data from station");
            return new CCFD(FireMarshall.get());
        }
        catch (Exception e) {
            System.out.println("Exception Get Data: " + e);
        }
        return new CCFD("{}");
    }

    protected abstract void handleResponse(HttpExchange httpExchange, CCFD ccfd)  throws  IOException;
}
