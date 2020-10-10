/**
 *
 */
package com.daviddemartini.servlet.burnban.Models;

import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CCFD {

    private Map<String,String> lastUpdated = new HashMap<>();
    private String burnStatus = "unknown";
    private boolean burnBanOff = false;

    public CCFD() {
        new CCFD("<html></html>");
    }
    public CCFD(String htmlContent){

        // define regex
        Pattern banLinePattern = Pattern.compile("<p><span class=.auto-style1.>.+Burn Ban is ([ONF]+).+</span></p>");
        Matcher matcher = banLinePattern.matcher("");
        // break content into lines to simplify regex matching
        String[] lines = htmlContent.split("[\n\r]");
        for(String line: lines){
            line.trim();
            if(line.length() > 0) {
                matcher = banLinePattern.matcher(line);
                if(matcher.find()){
                    burnStatus = matcher.group(1);
                    System.out.println("Ban is " + matcher.group(1));
                    // set boolean if there is a match
                    if(burnStatus == "OFF"){
                        burnBanOff = true;
                    }
                    break;
                }
            }
        }

        // add station metadata
        setLastUpdated();
    }

    /**
     * Set water flow sensor data
     *
     * @param burnStatus remote website raw html
     */
    public void setBurnStatus(String burnStatus) {
        this.burnStatus = burnStatus;
    }
    public String getBurnStatus(){
        return burnStatus;
    }

    /**
     * Flag that indicates if ban is ON or OFF
     * @param onoff
     */
    public void setBurnBanOff(boolean onoff){
        this.burnBanOff = onoff;
    }
    public boolean getBurnBanOff(){
        return burnBanOff;
    }

    /**
     *
     * @return
     */
    public Map<String,String> getLastUpdated(){
        return this.lastUpdated;
    }

    /**
     * Express object as a JSON string
     *
     * @return
     */
    public String toJson(){
        JSONObject json = new JSONObject();

        json.accumulate("burnStatus", burnStatus);
        json.accumulate("banOff", burnBanOff);
        json.accumulate("lastUpdated", new JSONObject( this.lastUpdated ));

        return json.toString(3);
    }

    /**
     *
     * @return
     */
    public String toString(){
        return getBurnStatus();
    }

    /**
     *
     */
    private void setLastUpdated(){
        long dateLong = new Date().getTime();
        LocalDateTime datetime = LocalDateTime.from(Instant.ofEpochMilli( dateLong )
                .atZone( ZoneId.systemDefault() ));
        lastUpdated.put("ZoneID",ZoneId.systemDefault().toString());
        lastUpdated.put("calendarDate",datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        lastUpdated.put("displayDate",datetime.format(DateTimeFormatter.ofPattern("E d-MMM HH:mm a")));
    }

}
