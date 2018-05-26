package org.rb.qa.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class was created to manage many knb xml files.
 * It manages xml file selection and keep information in
 * properties file.
 * The property file contains information about currently selected knb file.
 * /and list of information about knb file's title and filename./
 * Uses constants interface KNBSelectorConst.java
 * 
 * @author raitis
 */
final class KNBSelector {
    public final static String propFile = "knbs.prop";
    
    
    private int selectedKnb = 0;

    KNBSelector() {
       if( !new File(propFile).exists()) {
           try {
               writeProperties(0);
           } catch (IOException ex) {
               Logger.getLogger(KNBSelector.class.getName()).log(Level.SEVERE, null, ex);
               
           }
           
       }else {
           try {
               readProperties();
           } catch (FileNotFoundException ex) {
               Logger.getLogger(KNBSelector.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    
    
    
    private void readProperties() throws FileNotFoundException{
        Scanner sc = new Scanner(new File(propFile));
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
            String[] tokens = line.split("=");
            if(!tokens[0].equals(KNBSelectorConst.KEYS[2]))
                throw new RuntimeException("Wrong key token!");
            selectedKnb = Integer.parseInt(tokens[1]);
        }
    }
    private void writeProperties(int knbFileSelected) throws IOException{
        
        if( knbFileSelected < 0 || knbFileSelected> KNBSelectorConst.FILES.length-1 )
               throw new IOException("No Such KNB file's Index: "+knbFileSelected);
       
        //write to file
        
        File fi = new File(propFile);
        fi.createNewFile();
        PrintWriter writer = new PrintWriter(fi);
        
        writer.format("%s=%s\n", KNBSelectorConst.KEYS[2],knbFileSelected);
        writer.flush();
        writer.close();
        this.selectedKnb = knbFileSelected;
    }

    int getSelectedKnb() throws FileNotFoundException {
        readProperties();
        return selectedKnb;
    }

    void setSelectedKnb(int selectedKnb) throws IOException {
        writeProperties(selectedKnb);
        this.selectedKnb = selectedKnb;
        
    }
    
    List<String> getTitles(){
       return Arrays.asList(KNBSelectorConst.TITLES);
    }
    
    List<String> getFiles(){
      return Arrays.asList(KNBSelectorConst.FILES);
    }
    
    String getSelectedTitle(){
       return KNBSelectorConst.TITLES[selectedKnb];
    }
    
    String getSelectedFile(){
      return KNBSelectorConst.FILES[selectedKnb];
    }

    int getSelectedIdx() {
        return this.selectedKnb;
    }
}
