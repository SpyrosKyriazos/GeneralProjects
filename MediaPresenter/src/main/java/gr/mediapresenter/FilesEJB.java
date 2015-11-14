/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.mediapresenter;

import gr.mediapresenter.libs.MovieNamesSearcher;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.http.HttpManager;

/**
 *
 * @author Spyros
 */
@Singleton
@Startup
@Path("movies")
public class FilesEJB {

    private static final String path = "\\\\192.168.4.50\\movies\\Movies\\";
//    private static final String path = "/mnt/movies_folder";

    List<FileDTO> filesCollection = new ArrayList<FileDTO>();
    Map<String, String> jsonMovies = new HashMap<String, String>();

    @Lock(READ)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonMovies() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String value : jsonMovies.values()) {
            sb.append(value).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    @GET
    @Path("images")
    @Produces("image/*")
    public Response getImage(@QueryParam("url") String imageURL) {
        Object imgData = null;
        if (imageURL != null && imageURL.trim().length() > 0) {
            try {
                imgData = HttpManager.sendHTTPRequest(HttpManager.HTTP_REQUEST_METHOD.GET,
                        imageURL,
                        "image/*",
                        null,
                        false,
                        null,
                        null,
                        true);
            } catch (IOException ex) {
                Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Response.status(Response.Status.OK).entity(imgData == null ? "" : imgData).build();
    }

    @Schedule(second = "0", minute = "0", hour = "*", persistent = false)
    public void myTimer() {
        System.out.println(">>>>Timer event: " + new Date());
        init();
    }

    @PostConstruct
    public void onStart() {
        System.out.println(">>>>Timer event: " + new Date());
        init();
    }

    private void scanFiles() {
        try {
            File mainFolder = new File(path);
            File[] dateFolders = mainFolder.listFiles();//date or movies folders
            if (dateFolders != null) {

                Arrays.sort(dateFolders, new Comparator<File>() {

                    @Override
                    public int compare(File o1, File o2) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
                            Date date1 = sdf.parse(o1.getName());
                            Date date2 = sdf.parse(o2.getName());
                            if (date2.after(date1)) {
                                return -1;
                            } else {
                                return 1;
                            }
                        } catch (ParseException ex) {
                        }
                        return 0;
                    }
                });
                for (File dateFolder : dateFolders) {
                    if (dateFolder.isDirectory()) {
                        File[] movieFolders = dateFolder.listFiles();//movies folders
                        if (movieFolders != null) {
                            addFiles(dateFolder.getName(), movieFolders);
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private void scanFiles2() {
        try {
            MovieNamesSearcher mns = new MovieNamesSearcher();
            Collection<String[]> movieNames = mns.findMovieNames();
            for (String[] movieName : movieNames) {
                String dateFolderName = movieName[0];
                String movieFolderName = movieName[1];

                if (dateFolderName.equals(".")
                        || dateFolderName.equals("..")
                        || movieFolderName.equals(".")
                        || movieFolderName.equals("..")) {
                    continue;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
                long time = 0;
                try {
                    time = sdf.parse(dateFolderName).getTime();
                } catch (ParseException ex) {
                }
                FileDTO fileDTO = new FileDTO(dateFolderName, movieFolderName, time);
                filesCollection.add(fileDTO);
            }
        } catch (IOException ex) {
            Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for (FileDTO file : filesCollection) {
//            System.out.println("--" + file.getFileName());
//        }
    }

    private void addFiles(String dateFolderName, File[] files) {
        for (File movieFolder : files) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
            long time = 0;
            try {
                time = sdf.parse(dateFolderName).getTime();
            } catch (ParseException ex) {
            }
            FileDTO fileDTO = new FileDTO(dateFolderName, movieFolder.getName(), time);
            filesCollection.add(fileDTO);
        }
    }

    private void discoverMovies() {
//        System.out.println("Found " + filesCollection.size() + " files");

        //sort
        Collections.sort(filesCollection, new Comparator<FileDTO>() {

            @Override
            public int compare(FileDTO o1, FileDTO o2) {
                return (int) (o1.getTime() - o2.getTime());
            }
        });

        int counter = 0;
        for (FileDTO fileDTO : filesCollection) {
//            System.out.println(fileName);
            if (counter > 50) {
                break;
            }
            if (jsonMovies.get(fileDTO.getFileName()) != null) {
                continue;
            }
            String[] parts = parseName(fileDTO.getFileName());
            String title = parts[0];
            String year = parts[1];
            String json = callImdb(title, year);
            counter++;
            if (!json.isEmpty() && !json.contains("\"Response\":\"False\"")) {
                String path1 = path.replaceAll("\\\\", "/").substring(2) + fileDTO.getFolderName() + "/" + fileDTO.getFileName();
                String start = "{\"Path\":\"" + path1 + "\","
                        + "\"Time\":\"" + fileDTO.getTime() + "\",";
                json = start + json.substring(1);
                jsonMovies.put(fileDTO.getFileName(), json);
//                System.out.println(json);
            }
        }

//        System.out.println("Found " + jsonMovies.size() + " movies");
    }

    private String[] parseName(String name) {
        String[] parts = new String[2];

        String clean = "";
        String title = "";
        String year = "";

//        System.out.println("f- " + name);
        clean = name.replaceAll("\\.", " ").replaceAll("_", " ").replaceAll("-", " ").trim();

        Integer yearIndex = null;
        Pattern yearPattern = Pattern.compile("([\\[\\(]?((?:19[0-9]|20[01])[0-9])[\\]\\)]?)");
        Matcher yearMatcher = yearPattern.matcher(clean);
        if (yearMatcher.find()) {
            year = yearMatcher.group(1);
            yearIndex = clean.indexOf(year);
            year = year.replace("(", "").replace(")", "").replace("[", "").replace("]", "");
        }

        if (yearIndex != null) {
            title = clean.substring(0, yearIndex);
        } else {
            title = clean;
        }
        if (title.contains("aka")) {
            String[] titleParts = title.split("aka");
            title = titleParts[1].trim();
        }
        title = title.toLowerCase().replaceAll("extended", "").replaceAll("deluxe.*", "").trim().replaceAll(" +", " ");

//        System.out.println("c- " + clean);
//        System.out.println("y- " + year);
//        System.out.println("t- " + title);
        parts[0] = title;
        parts[1] = year;
        return parts;
    }

    private String callImdb(String title, String year) {
        String result = "";
        try {
            title = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        try {
            //        http://www.omdbapi.com/?t=Southpaw&y=2015&plot=short&r=json
            result = HttpManager.sendHTTPRequest(
                    HttpManager.HTTP_REQUEST_METHOD.GET,
                    "http://www.omdbapi.com/?t=" + title + "&y=" + year + "&plot=short&r=json",
                    "application/json",
                    null,
                    true);
        } catch (IOException ex) {
            Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("---" + result);
        return result;
    }

    private void dummyJSONCollection() {
        String string1 = "{\"Path\":\"192.168.4.50/movies/Movies/2014_06_05/The Grand Budapest Hotel[2014]\",\"Time\":\"1401915600000\",\"Title\":\"The Grand Budapest Hotel\",\"Year\":\"2014\",\"Rated\":\"R\",\"Released\":\"28 Mar 2014\",\"Runtime\":\"99 min\",\"Genre\":\"Adventure, Comedy, Drama\",\"Director\":\"Wes Anderson\",\"Writer\":\"Stefan Zweig (inspired by the writings of), Wes Anderson (screenplay), Wes Anderson (story), Hugo Guinness (story)\",\"Actors\":\"Ralph Fiennes, F. Murray Abraham, Mathieu Amalric, Adrien Brody\",\"Plot\":\"The adventures of Gustave H, a legendary concierge at a famous hotel from the fictional Republic of Zubrowka between the first and second World Wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.\",\"Language\":\"English, French\",\"Country\":\"USA, Germany, UK\",\"Awards\":\"Won 4 Oscars. Another 114 wins & 197 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMzM5NjUxOTEyMl5BMl5BanBnXkFtZTgwNjEyMDM0MDE@._V1_SX300.jpg\",\"Metascore\":\"88\",\"imdbRating\":\"8.1\",\"imdbVotes\":\"392,092\",\"imdbID\":\"tt2278388\",\"Type\":\"movie\",\"Response\":\"True\"}";
        String string2 = "{\"Path\":\"192.168.4.50/movies/Movies/2014_06_05/300 Rise of an Empire[2014]\",\"Time\":\"1401915600000\"\"Title\":\"300 Rise of an Empire: Becoming a Warrior\",\"Year\":\"2014\",\"Rated\":\"N/A\",\"Released\":\"24 Jun 2014\",\"Runtime\":\"N/A\",\"Genre\":\"Documentary, Short\",\"Director\":\"N/A\",\"Writer\":\"Eric Matthies, Alexandra E. Visconti\",\"Actors\":\"N/A\",\"Plot\":\"N/A\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"N/A\",\"Poster\":\"N/A\",\"Metascore\":\"N/A\",\"imdbRating\":\"N/A\",\"imdbVotes\":\"N/A\",\"imdbID\":\"tt3775646\",\"Type\":\"movie\",\"Response\":\"True\"}";
        String string3 = "{\"Path\":\"192.168.4.50/movies/Movies/2014_06_16/Enemy[2013]\",\"Time\":\"1402866000000\"\"Title\":\"Enemy\",\"Year\":\"2013\",\"Rated\":\"R\",\"Released\":\"06 Feb 2014\",\"Runtime\":\"91 min\",\"Genre\":\"Mystery, Thriller\",\"Director\":\"Denis Villeneuve\",\"Writer\":\"Jos? Saramago (novel), Javier Gull?n\",\"Actors\":\"Jake Gyllenhaal, M?lanie Laurent, Sarah Gadon, Isabella Rossellini\",\"Plot\":\"A man seeks out his exact look-alike after spotting him in a movie.\",\"Language\":\"English\",\"Country\":\"Canada, Spain, France\",\"Awards\":\"15 wins & 25 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTQ2NzA5NjE4N15BMl5BanBnXkFtZTgwMjQ4NzMxMTE@._V1_SX300.jpg\",\"Metascore\":\"61\",\"imdbRating\":\"6.8\",\"imdbVotes\":\"76,588\",\"imdbID\":\"tt2316411\",\"Type\":\"movie\",\"Response\":\"True\"}";
        jsonMovies.put("Dawn Of The Planet of The Apes[2014]", string1);
        for (int i = 0; i < 200; i++) {
            string1 = "{\"Path\":\"192.168.4.50/movies/Movies/2014_06_05/The Grand Budapest Hotel[2014]\",\"Time\":\"1401915600000\",\"Title\":\"The Grand Budapest Hotel" + i + "\",\"Year\":\"2014\",\"Rated\":\"R\",\"Released\":\"28 Mar 2014\",\"Runtime\":\"99 min\",\"Genre\":\"Adventure, Comedy, Drama\",\"Director\":\"Wes Anderson\",\"Writer\":\"Stefan Zweig (inspired by the writings of), Wes Anderson (screenplay), Wes Anderson (story), Hugo Guinness (story)\",\"Actors\":\"Ralph Fiennes, F. Murray Abraham, Mathieu Amalric, Adrien Brody\",\"Plot\":\"The adventures of Gustave H, a legendary concierge at a famous hotel from the fictional Republic of Zubrowka between the first and second World Wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.\",\"Language\":\"English, French\",\"Country\":\"USA, Germany, UK\",\"Awards\":\"Won 4 Oscars. Another 114 wins & 197 nominations.\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMzM5NjUxOTEyMl5BMl5BanBnXkFtZTgwNjEyMDM0MDE@._V1_SX300.jpg\",\"Metascore\":\"88\",\"imdbRating\":\"8.1\",\"imdbVotes\":\"392,092\",\"imdbID\":\"tt2278388\",\"Type\":\"movie\",\"Response\":\"True\"}";
            jsonMovies.put("Dawn Of The Planet of The Apes[2014]" + i, string1);
        }
//        jsonMovies.put("Grand Central", string2);
//        jsonMovies.put("Kenau", string3);
    }

    private void serializeData() {
        OutputStream file = null;
        try {
            File theFile = new File("movies_ser");
            file = new FileOutputStream(theFile);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(jsonMovies);
            output.flush();
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException ex) {
//                Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void deserializeData() {
        InputStream file = null;
        try {
            File theFile = new File("movies_ser");
            file = new FileInputStream(theFile);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            jsonMovies = (Map<String, String>) input.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException ex) {
//                Logger.getLogger(FilesEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void init() {
        deserializeData();
        filesCollection = new ArrayList<FileDTO>();
        scanFiles2();
        discoverMovies();
//        dummyJSONCollection();
//        soutJson();
        serializeData();
    }

    public static void main(String[] args) {
        FilesEJB filesEJB = new FilesEJB();
//        filesEJB.init();
//        System.out.println(filesEJB.getImage("http://ia.media-imdb.com/images/M/MV5BMTExMTUxNDQ5MjdeQTJeQWpwZ15BbWU4MDk4NTgxMzQx._V1_SX300.jpg"));
//        System.out.println(filesEJB.getJsonMovies());
    }

    private void soutJson() {
        System.out.println("----------------------------------------");

        for (String jsonMovie : jsonMovies.values()) {
            System.out.println(jsonMovie);
        }
    }

}
