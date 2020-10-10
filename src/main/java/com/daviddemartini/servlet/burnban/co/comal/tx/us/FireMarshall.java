/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daviddemartini.servlet.burnban.co.comal.tx.us;

/**
 *
 * @author david.demartini
 */
public class FireMarshall {

    private static final String FMS_URL = "https://www.co.comal.tx.us/Fire_Marshal/Burn_Information.htm";

    /**
     * Synthesize the FireMarshall CCFD Request URI
     *
     * @return
     */
    public static String get(){

	    // Create interface to retrieve the data
        Httpd httpd = new Httpd();

        return httpd.getString(FMS_URL);
    }

}

