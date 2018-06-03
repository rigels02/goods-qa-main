package org.rb.qa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raitis
 */
public class Resources {

    public List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (
                InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    public InputStream getResourceAsStream(String resource) {

        return getClass().getResourceAsStream(resource);
    }

    public void copyResourceToLocal(String sourceFile) throws IOException {
        if (!isAndroid()) {
            Path path = Paths.get(sourceFile).getFileName();
            //InputStream ins = getResourceAsStream("/assetsj/xmlTest_1.xml");
            InputStream ins = getResourceAsStream(sourceFile);
            if (ins == null) {
                throw new IOException("Resource/data file not available: " + sourceFile);
            }
            //Path path = Paths.get("xmlEx.xml");
            Files.copy(ins, path);
        } else {

            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    /**
     * Copy resource files by using resources list file 'assest-list.txt'.<br>
     * <h2>Description:</h2>
     * <pre>
     *  <b>if resources directory have the following structure: </b>
     *
     *   ──assetsj
     *  │   assets-list.txt
     *  │   knb.xml
     *  │   knb0.xml
     *  │   xmlTest.xml
     *  └───images
     *           icons8-html-48.png
     * </pre>
     * <b> Then assets-list.txt should have lines:</b><br>
     * <pre>
     * images/icons8-html-48.png
     * knb.xml
     * knb0.xml
     * xmlTest.xml
     * </pre>
     * <hr>
     *
     * @param sourceAssetsDir source resources directory in 'resource'
     * directory, must be with leading and ending '/' (ex.: /assetsj/).
     * @param targetDir target directory if any and without leading '/' to make
     * it in application directory
     * @throws IOException
     */
    public void copyResourceToLocalByListFile(String sourceAssetsDir, String targetDir) throws IOException {

        //put target directory without leading '/' to make it in application directory
        //leave as empty string if target directory is not needed
        Path targetAssetDir = Paths.get((targetDir == null) ? "" : targetDir);
        //ex. /assetsj/assets-list.txt
        String resourceListFile = sourceAssetsDir + "assets-list.txt";
        try (BufferedReader listFile = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream(resourceListFile),
                        StandardCharsets.UTF_8))) {

            String assetResource;
            while ((assetResource = listFile.readLine()) != null) {
                Path assetFile = targetAssetDir.resolve(assetResource);
                if (assetFile.getParent() != null) {
                    Path dir = Files.createDirectories(assetFile.getParent());
                }
                try (InputStream asset = getClass().getResourceAsStream(sourceAssetsDir + assetResource)) {
                    Files.copy(asset, assetFile);
                }
            }
        }
    }

    private boolean isAndroid() {
        return System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
    }

}
