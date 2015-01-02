/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer;

import java.util.HashMap;

/**
 *
 * @author YZY
 */
public abstract class Analyzer {
    
    /**
     * Method to summary the data.
     */
    abstract protected HashMap summary(HashMap<String, Data> hash);
}
