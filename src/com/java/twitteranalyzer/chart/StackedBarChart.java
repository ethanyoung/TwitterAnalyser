/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author YZY
 */
public class StackedBarChart extends JPanel{
 
    
    public StackedBarChart(String chartTitle, HashMap<Float, Integer> data1,HashMap<Float, Integer> data2, String topic1, String topic2, Dimension d) {
        super();
        
        CategoryDataset dataset = createDataset(data1, data2, topic1, topic2);
        
        JFreeChart chart = createBarChart(dataset, chartTitle);
        chart.setBackgroundPaint(new Color(236,233,216));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        
        chartPanel.setPreferredSize(new Dimension(d.width-15, d.height-20));

        this.add(chartPanel);
    }
    
    private DefaultKeyedValues2DDataset  createDataset(HashMap<Float, Integer> hash1,HashMap<Float, Integer> hash2, String topic1, String topic2) {

        DecimalFormat df = new DecimalFormat("0.0");

        ArrayList<Float> keys1 = new ArrayList<Float>(hash1.keySet());
        ArrayList<Float> keys2 = new ArrayList<Float>(hash1.keySet());

        Collections.sort(keys1);
        Collections.sort(keys2);

        float interval = keys1.get(1);

        DefaultKeyedValues2DDataset  dataset = new DefaultKeyedValues2DDataset ();

        /* For topic 1. */
        for(int i=0;i<keys1.size()-2;i++) {
            float key = keys1.get(i);
            dataset.addValue(-hash1.get(key), topic1, df.format(key)+"-"+df.format((key+interval)));
        }
        
        float secondLastKey1 = keys1.get(keys1.size()-2);
        float LastKey1 = keys1.get(keys1.size()-1);
        dataset.addValue(-hash1.get(secondLastKey1), topic1, df.format(secondLastKey1)+"-"+df.format(LastKey1));
        dataset.addValue(-hash1.get(LastKey1), topic1, df.format(LastKey1)+" or higher");
        
        /* For topic 2. */
        for(int i=0;i<keys2.size()-2;i++) {
            float key = keys2.get(i);
            dataset.addValue(hash2.get(key), topic2, df.format(key)+"-"+df.format((key+interval)));
        }
        
        float secondLastKey2 = keys2.get(keys2.size()-2);
        float LastKey2 = keys2.get(keys2.size()-1);
        dataset.addValue(hash2.get(secondLastKey2), topic2, df.format(secondLastKey2)+"-"+df.format(LastKey2));
        dataset.addValue(hash2.get(LastKey2), topic2, df.format(LastKey2)+" or higher");
        
        return dataset;
    }
    
    private JFreeChart createBarChart(CategoryDataset dataset, String title) {


        final JFreeChart chart = ChartFactory.createStackedBarChart(
            title,         // chart title
            "Value Group",               // domain axis label
            "Number of peple",        // range axis label
            dataset,                  // data
            PlotOrientation.HORIZONTAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        
        chart.setBorderPaint(Color.white);
        
        CategoryPlot plot = chart.getCategoryPlot();
        
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        /* Marker to mark the categouries. */
        CategoryMarker marker;

        marker = new CategoryMarker("4.0-10.0");
        marker.setLabel("Popular Person in Community");
        marker.setPaint(Color.YELLOW);
        marker.setAlpha(0.6f);
        marker.setLabelFont(new Font("SansSerif", Font.ITALIC, 11));
        marker.setLabelAnchor(RectangleAnchor.LEFT);
        marker.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
        marker.setLabelOffsetType(LengthAdjustmentType.CONTRACT);
        plot.addDomainMarker(marker, Layer.BACKGROUND);

        marker = new CategoryMarker("10.0 or higher");
        marker.setLabel("Super Star");
        marker.setPaint(Color.ORANGE);
        marker.setAlpha(0.6f);
        marker.setLabelFont(new Font("SansSerif", Font.ITALIC, 11));
        marker.setLabelAnchor(RectangleAnchor.LEFT);
        marker.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
        marker.setLabelOffsetType(LengthAdjustmentType.CONTRACT);
        plot.addDomainMarker(marker, Layer.BACKGROUND);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        return chart;
    }
}
