/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author YZY
 */
public class FileProcessor {
    
    /**
     * Save the hash table to a file, in JSON format.
     * @param file the file to save
     * @param hash the hash table HashMap<String, Data>
     */
    public void saveFile(File file, HashMap<String, Data> hash1, HashMap<String, Data> hash2) {
        try {
            
            ObjectMapper mapper = new ObjectMapper();
            
            /* Clear the content of the file. */
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("");
            
            /* Get data collection from hash1. */
            Collection<Data> data1 = hash1.values();
            
            for (Data data: data1) {
                
                /* FileWriter in append mode. */
                fw = new FileWriter(file, true);
                writer = new BufferedWriter(fw);
                writer.newLine();
                
                mapper.writeValue(writer, data);
            }
            
            /* Ready to write hash2. */
            fw = new FileWriter(file, true);
            writer = new BufferedWriter(fw);
            writer.newLine();
            writer.close();
            
            /* Get data collection fom hash2. */
            Collection<Data> data2 = hash2.values();
            
            for (Data data: data2) {
                
                /* FileWriter in append mode. */
                fw = new FileWriter(file, true);
                writer = new BufferedWriter(fw);
                writer.newLine();
                
                mapper.writeValue(writer, data);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Open a file, retrieve the data, which in JSON format, to a hash table.
     * @param file the file to open
     * @return the hash table HashMap<String, Data>.
     */
    public HashMap<String, Data>[] openFile(File file) {
        
        HashMap<String, Data>[] hashs = new HashMap[2];
        HashMap<String, Data> hash1 = new HashMap<String, Data>();
        HashMap<String, Data> hash2 = new HashMap<String, Data>();
        hashs[0] = hash1;
        hashs[1] = hash2;
        
        try {
            
            ObjectMapper mapper = new ObjectMapper();
            
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            
            String line = reader.readLine();
            
            line = reader.readLine();
            while (line != null) {
            
                /* If this is a blank line. */
                if (line.equals("")) {
                    break;
                }
                Data data = mapper.readValue(line, Data.class);

                hash1.put(data.ScreenName, data);
                
                line = reader.readLine();
            }
            
            /* Ready to read the second hash */
            line = reader.readLine();
            while (line != null) {
            
                Data data = mapper.readValue(line, Data.class);

                hash2.put(data.ScreenName, data);
                
                line = reader.readLine();
            }
            
            
        } catch (FileNotFoundException ex) {
            System.out.println("File "+file.getName()+" cannot be found in path \""+file.getPath()+"\"!");
            Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioe) {
            Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ioe);
        }
        
        return hashs;
    }
    
}
