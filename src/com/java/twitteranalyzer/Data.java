/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer;

import java.net.URL;

/**
 *
 * @author YZY
 */
public class Data {
        public String ScreenName;
        public float FriendFollowerRatio;
        public String Gender;
        public float MessageFrequency;
        public URL ProfileURL;
        
        public Data(){
            
        }
        
        public Data(String n, float r, String g, float f, URL u) {
            ScreenName = n;
            FriendFollowerRatio = r;
            Gender = g;
            MessageFrequency = f;
            ProfileURL = u;
        }
}
