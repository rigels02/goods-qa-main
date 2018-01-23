package org.rb.qa.utils;

import java.io.InputStream;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author raitis
 */
public class ResourcesTest {
    
    public ResourcesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getResourceFiles method, of class Resources.
     */
    //@Test
    public void testGetResourceFiles() throws Exception {
        System.out.println("getResourceFiles");
        String path = "";
        Resources instance = new Resources();
        List<String> expResult = null;
        List<String> result = instance.getResourceFiles(path);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResourceAsStream method, of class Resources.
     */
    //@Test
    public void testGetResourceAsStream() {
        System.out.println("getResourceAsStream");
        String resource = "";
        Resources instance = new Resources();
        InputStream expResult = null;
        InputStream result = instance.getResourceAsStream(resource);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyResourceToLocal method, of class Resources.
     */
    //@Test
    public void testCopyResourceToLocal() throws Exception {
        System.out.println("copyResourceToLocal");
        String sourceFile = "";
        Resources instance = new Resources();
        instance.copyResourceToLocal(sourceFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyResourceToLocalByListFile method, of class Resources.
     */
    //@Test
    public void testCopyResourceToLocalByListFile() throws Exception {
        System.out.println("copyResourceToLocalByListFile");
        Resources instance = new Resources();
        instance.copyResourceToLocalByListFile("/assetsj/",null);
        
    }
    
}
