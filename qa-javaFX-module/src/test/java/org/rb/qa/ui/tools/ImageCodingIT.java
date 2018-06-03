package org.rb.qa.ui.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static org.junit.Assert.*;

/**
 *
 * @author raitis
 */
public class ImageCodingIT {
    

    /**
     * Test of encodeToString method, of class ImageCoding.
     */
    @org.junit.Test
    public void testEncodeToString() throws IOException {
        String srcImage = "src\\test\\java\\org\\rb\\qa\\ui\\tools\\picture.png";
        String trgImage = "src\\test\\java\\org\\rb\\qa\\ui\\tools\\picture_1.png";
        File fiold = new File(trgImage);
        if(fiold.exists()){
         fiold.delete();
        }
        System.out.println("encodeToString");
        System.out.println("User Dir: "+System.getProperty("user.dir"));
        
        String imageString = ImageCoding.encodeImgToBase64String(srcImage);
        System.out.println("ImageString: "+imageString);
        System.out.println("decodeToImageFile");
        ImageCoding.decodeBase64StringToImg(imageString, trgImage);
        //assertTrue(image.equals(result));
        //assertEquals(image.toString(), result.toString());
       
        File fi1 = new File(srcImage);
        File fi2 = new File(trgImage);
        FileInputStream fis = new FileInputStream(srcImage);
        FileInputStream fis2 = new FileInputStream(trgImage);
        byte[] inbuf1 = new byte[(int)fi1.length()];
        fis.read(inbuf1);
        byte[] inbuf2 = new byte[(int)fi2.length()];
        fis2.read(inbuf2);
        
        assertTrue(fi1.length()==fi2.length());
        for(int i=0; i< inbuf1.length; i++){
            assertTrue(inbuf1[i]==inbuf2[i]);
        }
       
        //clean
         fis.close();fis2.close();
        fiold = new File(trgImage);
        if(fiold.exists()){
         fiold.delete();
        }
    }
    
    @org.junit.Test
    public void test_embeddedImageHtmlTag() throws IOException{
        System.out.println("test_embeddedImageHtmlTag()");
        String srcImage = "src\\test\\java\\org\\rb\\qa\\ui\\tools\\picture.png";
        //wrong extention
        try{
        ImageCoding.embeddedImageHtmlTag("src\\test\\java\\pic.zip");
        }catch(IllegalArgumentException ex){
            System.out.println("Expected exception...");
            assertTrue(ex.getMessage().equals("Wrong image file extention"));
        }
        try{
        ImageCoding.embeddedImageHtmlTag("src\\test\\java\\pic");
        }catch(IllegalArgumentException ex){
            System.out.println("Expected exception...");
            assertTrue(ex.getMessage().equals("Can not get file extension to get image type"));
        }
        //ok
        String htmlTag = ImageCoding.embeddedImageHtmlTag(srcImage);
        System.out.println("HtmTag => "+htmlTag);
    }

    
}
