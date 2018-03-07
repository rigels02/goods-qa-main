package org.rb.qa.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.rb.qa.model.KNBase;




/**
 * Uses XmlFactory to get application's xml deserializer
 * 
 * @author raitis
 */
public class KNBaseLoader {

    private static final Logger LOG = Logger.getLogger(KNBaseLoader.class.getName());
    
    private KNBase knBase;
    private IStorageFactory deserializerFactory;
    
    public KNBaseLoader(IStorageFactory factory) {
        deserializerFactory  = factory; //Get XML Deserializer
        this.knBase = new KNBase();
       // this.knBase.setQaList(new LinkedList<>());
       this.knBase.setQaList(new ArrayList<>());
    }
   
   
    private void load(String xmlFile) throws Exception{
      
        //KNBase knBase1 = (KNBase) deserializer.load(xmlFile);
        KNBase knBase1 = (KNBase) deserializerFactory.deSerialize(xmlFile);
        this.knBase.getQaList().addAll(knBase1.getQaList());
    }
    
    public KNBase loadByFiles(List<File> files) throws Exception{
        
        for (File file : files) {
            try {
                //load(file.getAbsolutePath());
                 load(file.getPath());
            } catch (JAXBException | FileNotFoundException ex) {
                Logger.getLogger(KNBaseLoader.class.getName()).log(Level.SEVERE, null, ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }
        }
        return knBase;
    }
    
    public KNBase loadByFileNames(List<String> fileList) throws Exception{
        for (String filename : fileList) {
            if( !Files.exists(Paths.get(filename))){
               throw new RuntimeException("File: "+filename+" not exists!");
            }
            try {
                load(filename);
            } catch (JAXBException | FileNotFoundException ex) {
                LOG.log(Level.SEVERE, null, ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }
        }
        return knBase;
    }
    public KNBase loadByDir(String sourceDirectory) throws Exception{
      if( !Files.exists(Paths.get(sourceDirectory))){
               throw new RuntimeException(""+sourceDirectory+" not exists!");
            }  
      if( !Files.isDirectory(Paths.get(sourceDirectory))){
         throw new RuntimeException(""+sourceDirectory+" is not a directory!");
      }
      DirectoryStream<Path> dirStream=null;
        try {
          dirStream = Files.newDirectoryStream(Paths.get(sourceDirectory), "*.xml");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        List<String> fnames= new ArrayList<>();
        for (Path file : dirStream) {
            fnames.add(file.toString());
        }
        loadByFileNames(fnames);
        return knBase;
    }
    
    public KNBase loadFromStream(InputStream ins) throws JAXBException{
      JAXBContext context = JAXBContext.newInstance(KNBase.class);
        Unmarshaller um = context.createUnmarshaller();
        return (KNBase) um.unmarshal(ins);
    }
    
    public KNBase loadFromFile(String fileName) throws Exception {
        List<String> flist = new ArrayList<>();
        flist.add(fileName);
        return loadByFileNames(flist);
    }

    
}
