package com.daviddemartini.servlet.burnban;

import com.daviddemartini.servlet.burnban.Server.AppServer;
import com.daviddemartini.servlet.burnban.Handler.JSON;
import com.daviddemartini.servlet.burnban.Handler.HTML;

import java.io.IOException;
import java.util.Map;


/**
 * Comal County Burn Ban Parsing Service
 */
public class App {

    private static String HOSTNAME = "localhost";
    private static int PORT = 8820;
    private static AppServer Server;

    public static void main(String[] args) throws IOException {

        // Read environment variables if set
        readEnv();

        // Construct the Server
        Server = new AppServer(HOSTNAME,PORT);
        Server.addHandler("/json", new JSON());
        Server.addHandler("/html", new HTML());

        // Start server
        Server.start();

        System.out.println("\n** RUNNING **\n");
    }


    /**
     * Use environment variables if they were set
     *
     */
    private static void readEnv() {

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            switch (envName) {
                case "RF_HOSTNAME":
                    HOSTNAME = env.get("RF_HOSTNAME").toString();
                    break;
                case "RF_PORT":
                    int intPORT = Integer.parseInt(env.get("RF_PORT").toString());
                    PORT = (intPORT > 0) ? intPORT : PORT;
                    break;
            }
        }
    }
}
