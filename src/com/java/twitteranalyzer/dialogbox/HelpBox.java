/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AboutBox.java
 *
 * Created on 05/10/2012, 9:17:16 PM
 */
package com.java.twitteranalyzer.dialogbox;

/**
 *
 * @author YZY
 */
public class HelpBox extends javax.swing.JDialog {

    /** Creates new form AboutBox */
    public HelpBox(java.awt.Frame parent) {
        super(parent);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setTitle("How to use...");
        setResizable(false);

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jTextArea1.setText("Before searching, user should specify the\ntopics and tweets per page first.\n\nTo specify the topic, just fill the topic \nfield. In twitter, topics are starting with\n\"#\", e.g. \"#Game\" means topic of Game.\n\nTo specify thetweets per page, just fill the\nrelevant text field. Remember the number must\nbe between 0 and 100. The application searches\n10 pages every time. Therefore the total \nnumber of tweet for each topic is n*10 (n is\nthe tweets per page specified by user).\n\nWhen the number of tweets per page is beggger\nthan 10, the application takes a comparably\nlong time to do the searching (a few minutes).\nPlease be patient for that.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
