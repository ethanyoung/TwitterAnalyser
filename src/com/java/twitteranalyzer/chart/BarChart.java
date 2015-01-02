/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.twitteranalyzer.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.HashMap;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author YZY
 */
public class BarChart extends JPanel{
 
    
    public BarChart(String chartTitle, HashMap<String, Integer> data, String topic, Dimension d, Color color) {
        super();
        
        CategoryDataset dataset = createDataset(data, topic);
        
        JFreeChart chart = createBarChart(dataset, chartTitle, color);
        chart.setBackgroundPaint(new Color(236,233,216));

        ChartPanel chartPanel = new ChartPanel(chart);
        
        chartPanel.setPreferredSize(new Dimension((d.width-15)/2, d.height-20));

        this.add(chartPanel);
    }
    
    private CategoryDataset createDataset(HashMap<String, Integer> hash, String topic) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for(String key: hash.keySet()) {
            dataset.addValue(hash.get(key), topic, key);
        }
        
        
        return dataset;
    }
    
    private JFreeChart createBarChart(CategoryDataset dataset, String title, Color color) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            title,         // chart title
            null,               // domain axis label
            "Number of peple",        // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        
        chart.setBorderPaint(Color.white);
        
        CategoryPlot plot = chart.getCategoryPlot();

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        plot.setForegroundAlpha(0.8f);


        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, color);


        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
         final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );

        return chart;
    }
}
