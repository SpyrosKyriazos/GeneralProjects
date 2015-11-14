/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.mediapresenter;

/**
 *
 * @author user
 */
public class FileDTO {

    private String folderName;
    private String fileName;
    private long time;

    public FileDTO() {
    }

    public FileDTO(String folderName, String fileName, long time) {
        this.folderName = folderName;
        this.fileName = fileName;
        this.time = time;
    }
    
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
