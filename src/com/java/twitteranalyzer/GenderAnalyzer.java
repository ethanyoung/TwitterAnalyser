/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import twitter4j.User;

/**
 *
 * @author YZY
 */
public class GenderAnalyzer extends Analyzer{
    
    /* File to be read. */
    final String MALEFILE = "res\\namedata\\census1990_male.csv";
    final String FEMALFILE = "res\\namedata\\census1990_female.csv";
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String UNKNOWN = "Unknown";
    public static final String NEUTRAL = "Neutral";
    
    ArrayList<Rank> maleRank;
    ArrayList<Rank> femaleRank;
    
    public GenderAnalyzer() {
        
        maleRank = readFile(MALEFILE);
        femaleRank = readFile(FEMALFILE);
        
    }
    
    public String determine(User user) {
        
        /* Get the user name in the name field. */
        String userFullName = user.getName();
        String[] userNames = userFullName.toLowerCase().split(" ");

        boolean isMale = false;
        boolean isFemale = false;
        
        /* For each part of name. */
        for(String userName: userNames) {
            
            /* Search male data. */
            for (Rank r: maleRank) {
                if (userName.equals(r.name)) {
                    isMale = true;
                    break;
                }
            }
            
            /* Search femal data. */
            for (Rank r: femaleRank) {
                if (userName.equals(r.name)) {
                    isFemale = true;
                    break;
                }
            }
            
            /* Once gender has been determined, break the loop. */
            if (isMale || isFemale) {
                break;
            }
        }

        if (isMale && !isFemale) {
            return MALE;
        }
        else if (!isMale && isFemale) {
            return FEMALE;
        }
        else if (isMale && isFemale) {
            return NEUTRAL;
        }
        else {
            return UNKNOWN;
        }
    }
    
    @Override
    protected HashMap<String, Integer> summary(HashMap<String, Data> hash) {
        
        HashMap<String, Integer> result = new HashMap<String, Integer>();
      
        int countmale = 0;
        int countfemale = 0;
        int countunknown = 0;
        
        Collection<Data> datas = hash.values();
        
        for (Data data: datas) {
            if (data.Gender.equals(MALE)) {
                countmale++;
            }
            else if(data.Gender.equals(FEMALE)) {
                countfemale++;
            }
            
            /* If the gender is neutral. */
            else if (data.Gender.equals(NEUTRAL)) {
                
                /* Each gender increase one. */
                countmale++;
                countfemale++;
            }
            else {
                countunknown++;
            }
        }
         
        result.put(UNKNOWN, countunknown);
        result.put(MALE, countmale);
        result.put(FEMALE, countfemale);
        
        return result;
    }
    
    /**
     * Read file content, return a list of Rank
     * @param filename
     * @return a list of Rank data
     */
    private ArrayList<Rank> readFile(String filename) {
        
        ArrayList<Rank> content = new ArrayList<Rank>();
        
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();

            while (line != null) {
                String[] lineArray = line.split(",");
                Rank r = new Rank (lineArray[0], Integer.valueOf(lineArray[1]));
                content.add(r);
                
                line = reader.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        return content;
    }
    
    /**
     * Simple class store ranking information. 
     */
    class Rank {
        String name;
        int rank;
        
        public Rank(String name, int rank) {
            this.name = name;
            this.rank = rank;
        }
    }
}
