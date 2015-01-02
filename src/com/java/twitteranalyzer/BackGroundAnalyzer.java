/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

/**
 *
 * @author YZY
 */
public class BackGroundAnalyzer {
 
    Twitter twitter;
    int appIndex;
    int duplicated;
    final int NUMBER_OF_APPS = 6;

    /* Contains all the tweets */
    ArrayList<Tweet> mMessageCorpus;
    
    /* Contains all the user information for topic 1. */
    HashMap<String, Data> mHashMap;
   
    GenderAnalyzer mGenderAnalyzer;
    DailyActivityAnalyzer mDailyActivityAnalyzer;
    FollowerFriendRatioAnalyzer mFollowerFriendRatioAnalyzer;
    ProfileURLAnalyzer mProfileURLAnalyzer;
    
    public BackGroundAnalyzer() {
        twitter = new TwitterFactory().getInstance();
        
        duplicated = 0;
        
        appIndex = 0;
        twitter.setOAuthConsumer(OAuth.CunsumerKey[appIndex], OAuth.ConsumerSecret[appIndex]);
        twitter.setOAuthAccessToken(new AccessToken(OAuth.AccessToken[appIndex],OAuth.TokenSecret[appIndex]));
        appIndex++;
        
        mHashMap = new HashMap<String, Data>();
        mMessageCorpus = new ArrayList<Tweet>();
        
        mGenderAnalyzer = new GenderAnalyzer();
        mDailyActivityAnalyzer = new DailyActivityAnalyzer();
        mFollowerFriendRatioAnalyzer = new FollowerFriendRatioAnalyzer();
        mProfileURLAnalyzer = new ProfileURLAnalyzer();
    }
    
    
    /**
     * Get a lot of message for a topic.
     * @param topic the topic to search
     */
    public boolean GetMessageCorpus(String topic, int rpp) {
        
        
        try {
            Query query = new Query(topic);
            
            /* set tweets per page. */
            query.rpp(rpp);
            
            /* For each page, 10 pages in total.  */
            for (int i=1;i<11;i++) {
                
                query.setPage(i);
                
                /* Get the result on that page. */
                QueryResult result = twitter.search(query);
                
                /* Get the tweets of the result. */
                List<Tweet> tweets = result.getTweets();
                
                /* For each tweet */
                for (Tweet tweet : tweets) {
                    //User user = twitter.showUser(tweet.getFromUser());
                    mMessageCorpus.add(tweet);
                    //System.out.println(user.getScreenName());
                    //System.out.println("@" + tweet.getFromUser() + " - " + tweet.getText());
                }
                
                //System.out.println(tweets.size());
            }
            
            return true;
        } catch (TwitterException te) {
            
            if (te.exceededRateLimitation() && appIndex<NUMBER_OF_APPS) {
                
                changeToken();
                GetMessageCorpus(topic, rpp);
                
            }
            
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
                
            return false;
        }
    }
    
    private void changeToken(){
        try {
            System.out.println(twitter.getRateLimitStatus().toString());
        } catch (TwitterException ex) {
            Logger.getLogger(BackGroundAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(OAuth.CunsumerKey[appIndex], OAuth.ConsumerSecret[appIndex]);
        twitter.setOAuthAccessToken(new AccessToken(OAuth.AccessToken[appIndex],OAuth.TokenSecret[appIndex]));
        appIndex++;
    }
    /**
     * Analyze the messages
     * @param messagecorpus messages to be analyzed 
     */
    public boolean MessageStats(ArrayList<Tweet> messagecorpus) {
        
        System.out.println("MessageCorp size: "+messagecorpus.size());
        
        /* Clear the hashmap. */
        mHashMap.clear();
        
        for (Tweet message: messagecorpus) {
            if (!UserDemographics(message)){
                return false;
            }
        }
        
        System.out.println("Users size: "+mHashMap.size());
        
        return true;
    }
    
    public void printHash(HashMap<String, Data> hash) {
        System.out.println(hash.size());
        for (String key: hash.keySet()) {
            System.out.println(key+": "+hash.get(key).Gender);
        }
    }
    
    /**
     * Find the user property of author of each message.
     * @param message 
     */
    public boolean UserDemographics(Tweet message) {
        
        try{
            
            String username = message.getFromUser();
            
            if (isAnalyzed(username, mHashMap)) {
                
                duplicated++;
                // retrieve result from hash
                return true;
            }
            
            /* Get the user entity */
            User user = twitter.showUser(username);
            
            /* Get FollowerFriendRatio */
            float ratio = getFollowrFriendRatio(user);
            
            /* Get gender */
            String gender = determinGender(user);
            
            /* Get message frequency */
            float frequency = getMessageFrequency(user);
            
            /* Get profile URL */
            URL url = getProfileURLs(user);
            
            /* Cache the data */
            Data data = new Data(username, ratio, gender, frequency, url);
            cacheResult(username, data, mHashMap);
            
            //saveResult();
            return true;
            
        } catch (TwitterException te) {
            
            if (te.exceededRateLimitation() && appIndex<NUMBER_OF_APPS) {
                
                changeToken();
                UserDemographics(message);
                return true;
            }
            else {
                te.printStackTrace();
                System.out.println("Failed to find users: " + te.getMessage());
                return false;
            }
        }
        
    }
    
    public HashMap<String, Integer> summaryGenderData(HashMap<String, Data> hash) {
        return mGenderAnalyzer.summary(hash);
    }
    
    public HashMap<Float, Integer> summaryMessageFrequencyData(HashMap<String, Data> hash) {
        return mDailyActivityAnalyzer.summary(hash);
    }
    
    public HashMap<Float, Integer> summaryFollowerFriendRatio(HashMap<String, Data> hash) {
        return mFollowerFriendRatioAnalyzer.summary(hash);
    }
    
    public HashMap<String, Integer> summaryProfileURL(HashMap<String, Data> hash) {
        return mProfileURLAnalyzer.summary(hash);
    }
    
    public String determinGender(User user) {
        return mGenderAnalyzer.determine(user);
    }
    
    public float getFollowrFriendRatio(User user){
        return mFollowerFriendRatioAnalyzer.getRatio(user);
    }
    
    private float getMessageFrequency(User user) {
        return mDailyActivityAnalyzer.getMessageFrequency(user);
    }
    
    private URL getProfileURLs(User user) {
        return mProfileURLAnalyzer.getURL(user);
    }
    
    private boolean isAnalyzed(String username, HashMap<String, Data> hashtable) {
        if (hashtable.containsKey(username)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private void cacheResult(String username, Data data, HashMap<String, Data> hashmap) {
        hashmap.put(username, data);
    }
}
