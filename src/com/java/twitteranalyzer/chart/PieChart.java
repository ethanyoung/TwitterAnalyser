/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer.chart;


import java.awt.Color;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author YZY
 */
public class PieChart extends JPanel{
    
    private static final long serialVersionUID = 1L;
    
    public PieChart(String chartTitle, HashMap<String, Integer> data, Dimension d) {
        super();
        
        PieDataset dataset = createDataset(data);
        
        JFreeChart chart = createPieChart(dataset, chartTitle);
        chart.setBackgroundPaint(new Color(236,233,216));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        
        chartPanel.setPreferredSize(new Dimension((d.width-15)/2, d.height-20));

        this.add(chartPanel);
    }
    
    
    
    private  PieDataset createDataset(HashMap<String, Integer> hash) {
        DefaultPieDataset result = new DefaultPieDataset();
        
        ArrayList<String> keyList = new ArrayList<String>(hash.keySet());
        Collections.sort(keyList);
        
        for(String key: keyList) {
            result.setValue(key, hash.get(key));
        }
        return result;
        
    }
 
    private JFreeChart createPieChart(PieDataset dataset, String title) {
        
        JFreeChart chart = ChartFactory.createPieChart(
            title,          // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(180);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.8f);
        
        plot.setBackgroundPaint(Color.white);
        
        /* Display values */
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}= {1}({2})",
                NumberFormat.getNumberInstance(),
                NumberFormat.getPercentInstance()
        ));
        
        plot.setNoDataMessage("No data available");
        return chart;
        
    }
}
