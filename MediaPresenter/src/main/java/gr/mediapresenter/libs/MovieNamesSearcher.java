/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.mediapresenter.libs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author user
 */
public class MovieNamesSearcher {

    public Collection<String[]> findMovieNames() throws IOException, InterruptedException {
        Collection skipFolderNames = new ArrayList();
        skipFolderNames.add("Older_Movies");
        return findMovieNames("//192.168.4.50/movies", "Movies/*", skipFolderNames);
    }

    public Collection<String[]> findMovieNames(String rootPath, String subPath, Collection<String> skipFolderNames) throws IOException, InterruptedException {
        Collection<String[]> results = new ArrayList();
//        System.out.println("search first level\n\n\n\n");
        Collection<String> firstLevelFolderNames = searchIn(rootPath, subPath);
        for (String firstLevelFolderName : firstLevelFolderNames) {
            if (skipFolderNames.contains(firstLevelFolderName)) {
                continue;
            }
//            System.out.println("\n\n\n\n");
//            System.out.println("search movies level\n\n\n\n");
            Collection<String> moviesFolderNames = searchIn(rootPath, subPath.replace("*", firstLevelFolderName + "/*"));
//            System.out.println("folder:" + firstLevelFolderName + ", movies:" + moviesFolderNames.size());
            for (String moviesFolderName : moviesFolderNames) {
                results.add(new String[]{firstLevelFolderName, moviesFolderName});
            }
        }
        return results;
    }

    public Collection<String> searchIn(String rootPath, String subPath) throws IOException, InterruptedException {
        Collection<String> folderNames = new ArrayList();
        Runtime r = Runtime.getRuntime();
        String theCommand = "smbclient \"" + rootPath + "\" -N -c \"ls " + subPath + "\"";
//        System.out.println(theCommand);
        Process p = r.exec(new String[]{"/bin/sh", "-c", theCommand});
        p.waitFor();
        BufferedReader bufferedReader = null;
        InputStreamReader inReader = null;
        InputStream in = null;
        try {
            in = p.getInputStream();
            inReader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String folderName = line.split(" D ").length > 1 ? line.split(" D ")[0].trim() : "";
//                if (folderName.length() == 0) {
//                    folderName = line.split("A")[0].trim();
//                }
                if (folderName.length() != 0) {
                    folderNames.add(folderName);
                }
//                System.out.println(folderName);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (inReader != null) {
                inReader.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return folderNames;
    }
}
