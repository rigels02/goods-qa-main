package org.rb.qa.ui.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;


/**
 *
 * @author raitis
 */
public class ImageCoding {

    private ImageCoding() {
    }
    
 
    /**
     * 
     * @param imagePath
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static String encodeImgToBase64String(String imagePath) 
            throws FileNotFoundException, IOException {
	String base64Image = "";
	File file = new File(imagePath);
	try (FileInputStream imageInFile = new FileInputStream(file)) {
		// Reading a Image file from file system
		byte[] imageData = new byte[(int) file.length()];
		imageInFile.read(imageData);
		base64Image = Base64.getEncoder().encodeToString(imageData);
	} catch (FileNotFoundException e) {
		throw  new FileNotFoundException("Image not found: " +imagePath);
	} catch (IOException ioe) {
		
                throw new IOException("Exception while reading the Image "+imagePath);
	}
	return base64Image;
}
    /**
     * Html embedded image is in form like:
     * &lt;img src="data:image/png;base64,iVBORw0KGgo...alt="picture.png" scale="0"&gt;
     * Extract substring after 'data:image/png;base64,' and pass as parameter
     * imageString
     * @param base64Image
     * @param pathFile
     * @throws java.io.FileNotFoundException 
     */
    public static void decodeBase64StringToImg(String base64Image, String pathFile) 
            throws FileNotFoundException, IOException {
	try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
		// Converting a Base64 String into Image byte array
		byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
		imageOutFile.write(imageByteArray);
	} catch (FileNotFoundException e) {
		throw new FileNotFoundException("Problem open file:"+pathFile+"\n"+e.getMessage());
                
	} catch (IOException ioe) {
		
                throw new IOException("Exceptiom writing to file: "+pathFile+"\n"+ioe.getMessage());
	}
}
 
    public static String embeddedImageHtmlTag(String pathFile)
        throws IllegalArgumentException, IOException{
    
        final String fmt= "<img src=\"data:image/%s;base64,%s\" alt=\"img\" />";
        String imgTag = null;
        
        String[] parts = pathFile.split("\\.");
        if(parts.length<2){
          throw new IllegalArgumentException("Can not get file extension to get image type");
        }
        String ext = parts[parts.length-1].toLowerCase().trim();
        if( !(ext.equals("png") 
                || ext.equals("jpg") 
                || ext.equals("jpeg") 
                || ext.equals("gif")
                || ext.equals("bmp")) ){
            throw new IllegalArgumentException("Wrong image file extention");
        }
        String base64String = encodeImgToBase64String(pathFile);
        imgTag= String.format(fmt, ext,base64String);
        return imgTag;
    }
}
