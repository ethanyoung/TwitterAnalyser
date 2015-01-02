/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import twitter4j.User;

/**
 *
 * @author YZY
 */
public class DailyActivityAnalyzer extends Analyzer{
    
    public float getMessageFrequency(User user) {
        
        /* Create date of the account. */
        Calendar createDate = Calendar.getInstance();
        createDate.setTime(user.getCreatedAt());
        
        /* Current date. */
        Calendar currentDate = Calendar.getInstance();
        
        /* Difference between create date and current date. */
        long diffDays = (currentDate.getTimeInMillis() - createDate.getTimeInMillis()) /
                (24 * 60 * 60 * 1000);
        
        /* Get total status. */
        int statusCount = user.getStatusesCount();
        
        float daily = 0f;
        
        /* If the days is not 0. */
        if (diffDays != 0) {
            return daily = statusCount / diffDays;
        }
        
        return daily;
    }

    @Override
    protected HashMap<Float, Integer> summary(HashMap<String, Data> hash){
        

        final int NUMBER_OF_INTERVALS = 20;
        int[] count = new int[NUMBER_OF_INTERVALS];
        float INTERVAL = 5;

        HashMap<Float, Integer> result = new HashMap<Float, Integer>();

        ArrayList<Float> dailys = new ArrayList<Float>();

        Collection<Data> datas = hash.values();

        for (Data data: datas) {

            float ratio = data.MessageFrequency;
            dailys.add(ratio);

        }

        Collections.sort(dailys);

        for (float daily: dailys) {
            int index = (int)Math.floor(daily/INTERVAL);
            if (index >= NUMBER_OF_INTERVALS) {
                count[count.length-1]++;
            }

            else {
                count[index]++;
            }
        }

        for (int i=0; i<NUMBER_OF_INTERVALS; i++) {
            result.put(0+INTERVAL*i, count[i]);
        }


        return result;
    }
}
