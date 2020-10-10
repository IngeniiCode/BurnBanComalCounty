package com.daviddemartini.servlet.burnban.Handler;

import com.daviddemartini.servlet.burnban.Server.RequestHandler;
import com.daviddemartini.servlet.burnban.Models.CCFD;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class JSON extends RequestHandler {

    /**
     *
     * @param httpExchange
     * @throws IOException
     */
    @Override
    protected void handleResponse(HttpExchange httpExchange, CCFD ccfd)  throws  IOException {
        String content = ccfd.toJson();
        httpExchange.setAttribute("Content-Type", "application/json; charset=UTF-8");
        httpExchange.sendResponseHeaders(200, content.length());

        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
