package com.java.twitteranalyzer;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import twitter4j.User;

/**
 * Contains the methods for user profile url analyzer.
 *
 * @author YZY
 */
public class ProfileURLAnalyzer extends Analyzer{

    /**
     * Get URL from user
     * @param user
     * @return 
     */
    public URL getURL(User user){
        URL url = user.getURL();
        
        return url;
    }
    
    @Override
    protected HashMap<String, Integer> summary(HashMap<String, Data> hash) {

        /* LinkedHashMap keeps the keys in the order of inserting. */
        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
        
        Collection<Data> datas = hash.values();
        
        for (Data data: datas) {
                
            String hostName;

            /* We need to make sure the url is not null. */
            if(data.ProfileURL != null) {

                /* Get the host name of the URL. */
                hostName = data.ProfileURL.getHost();

                /* If the hostName has been saved. */
                if (result.containsKey(hostName)) {

                    /* Get the old value. */
                    int v = result.get(hostName);
                    v++;

                    /* Put the new value. */
                    result.put(hostName, v);
                }

                /* If this is a new hostName*/
                else {
                    result.put(hostName, 1);
                }
            }
        }

        /* Put the result into a ArrayList for sorting. */
        ArrayList<Result> resultList = new ArrayList<Result>();
        for(String key: result.keySet()) {
            resultList.add(new Result(key, result.get(key)));
        }
        
        Collections.sort(resultList);
        result.clear();

        /* We only care about the top 10 domains in the result. */
        if (resultList.size()>10) {
            for (int i=0;i < 10; i++) {
                Result r = resultList.get(i);
                result.put(r.domainName, r.frequency);
            }
        }

        else {
            for (Result r: resultList) {
                result.put(r.domainName, r.frequency);
            }
        }
        
        return result;
    }

    /**
     * This class is only for sorting the result.
     */
    class Result implements Comparable{
        String domainName;
        int frequency;
        public Result(String d, int f){
            domainName = d;
            frequency = f;
        }

        /**
         * Sort the elements by frequency in reverse order.  
         * @param o
         * @return 
         */
        @Override
        public int compareTo(Object o) {
            if (this.frequency == ((Result) o).frequency) {
                return 0;
            }
            else if (this.frequency > ((Result) o).frequency) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }
}