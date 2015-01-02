/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import twitter4j.User;

/**
 *
 * @author YZY
 */
public class FollowerFriendRatioAnalyzer extends Analyzer{

    public float getRatio(User user){
        int follower = user.getFollowersCount();
        int friend = user.getFriendsCount();
        
        //System.out.println(follower+" "+friend);
        float ratio = 0f;
         
        if (friend != 0) {
            ratio = (float) follower/friend;
        }
        
        return ratio;
    }
    
    @Override
    protected HashMap<Float, Integer> summary(HashMap<String, Data> hash) {
        
        final int NUMBER_OF_INTERVALS = 20;
        int[] count = new int[NUMBER_OF_INTERVALS+2];
        final float INTERVAL = 0.2f;
        final float LARGE = 10.0f;
        
        HashMap<Float, Integer> result = new HashMap<Float, Integer>();
        
        ArrayList<Float> ratios = new ArrayList<Float>();
        
        Collection<Data> datas = hash.values();
        
        for (Data data: datas) {
            
            float ratio = data.FriendFollowerRatio;
            ratios.add(ratio);
            
        }
        
        Collections.sort(ratios);
        
        for (float ratio: ratios) {
            if (ratio >= LARGE) {
                count[count.length-1]++;
            }
            else if(ratio >= NUMBER_OF_INTERVALS * INTERVAL) {
                count[count.length - 2]++;
            }
            else {
                int index = (int)Math.floor(ratio/INTERVAL);
                count[index]++;
            }
        }
        
        for (int i=0; i<NUMBER_OF_INTERVALS; i++) {
            result.put(0+INTERVAL*i, count[i]);
        }
        
        result.put(NUMBER_OF_INTERVALS * INTERVAL, count[count.length-2]);
        result.put(LARGE, count[count.length-1]);
        
        return result;
    }
    
}
