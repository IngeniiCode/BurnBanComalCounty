package com.daviddemartini.servlet.burnban.Handler;

import com.daviddemartini.servlet.burnban.Server.RequestHandler;
import com.daviddemartini.servlet.burnban.Models.CCFD;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HTML extends RequestHandler {

    private final String TITLE = "Comal County Burn Ban Status";

    /**
     *
     * @param httpExchange
     * @throws IOException
     */
    @Override
    protected void handleResponse(HttpExchange httpExchange, CCFD ccfd)  throws  IOException {

        String burnBanStatus = String.format(
                "<span class='banPrefix'>Burn ban </span><span class='%s'>%s</span>",
                (ccfd.getBurnBanOff()) ? "banOFF" : "banON",
                ccfd.getBurnStatus());

        String content = String.format(htmlTemplate(),
                TITLE,
                cssStyles(),
                javaScript(),
                TITLE,
                ccfd.getLastUpdated().get("displayDate").toString(),
                burnBanStatus
                );

        httpExchange.setAttribute("Content-Type", "text/html; charset=UTF-8");
        httpExchange.sendResponseHeaders(200, content.length());

        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    /**
     * Define styles use for content
     *
     * @return
     */
    private String cssStyles(){

        return  "  body { background-color: #003366; color: white; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: center; height: 230px; }" +
                "  section { padding: 10px; border: gray 1px dashed; height: 100%; }" +
                "  div.phone { font-weight: 200; } " +
                "  a.phone { font-size: 1.5em ; font-weight: 600; color: inherit; font-family: inherit; text-decoration: none;  }" +
                "  span.banPrefix { font-size: .8em; }" +
                "  span.banON { font-weight: 600; color: red; }" +
                "  span.banOff { font-weight: 600; color: green; }" +
                "  div.header #location { font-size: 1em; margin: 0; padding 0;} " +
                "  div.header #datetime { font-size: 2em; font-weight: strong; margin: 0; padding 0;}" +
                "  div.header em { font-size: .5em; }" +
                "  div.content { font-size: 10vw; font-weight: 400; } ";
    }

    private String javaScript(){
        return "";
    }

    /**
     * HTML template content
     *
     * @return
     */
    private String htmlTemplate(){
        return  "<html>" +
                "  <head>" +
                "    <title>%s</title>" +
                "    <meta http-equiv='refresh' content='600'/>" +
                "    <style>%s</style> " +
                "    <script language='JavaScript'>%s</script>" +
                "  </head>" +
                "  <body>" +
                "    <section>" +
                "      <div class='header'><div id='location'>%s</div> " +
                "      <div id='datetime'>%s</div></div> " +
                "      <div class='content'>%s</div> " +
                "      <div class='phone'>phone: <a class='phone' href='tel:8306433748'>830-643-3748</a></div>" +
                "    </section>" +
                "  </body>" +
                "</html>";

    }
}
