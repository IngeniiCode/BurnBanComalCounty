/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daviddemartini.servlet.burnban.co.comal.tx.us;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author david.demartini
 */
public class Httpd {

    public Httpd() {
        // perform some setup if required
    }

    /**
     *
     * @param uri
     * @return
     */
    public String getString(String uri){

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            System.err.println("HTTP Error connection to " + uri + " " + e.getMessage());
        }

        return "";
    }

}
