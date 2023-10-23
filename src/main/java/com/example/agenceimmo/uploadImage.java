package com.example.agenceimmo;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class uploadImage {
    private String serveur = "172.19.0.35";
    private String user = "sio";
    private String password= "0550002D";
    private int port = 21;
    private String remotePath = "./agenceImmo/";

    FTPClient ftpClient;

    public uploadImage(){
        ftpClient = new FTPClient();
    }
    public void init() throws IOException {
        this.ftpClient.connect(serveur,port);
        this.ftpClient.login(user,password);
        this.ftpClient.enterLocalPassiveMode();
        this.ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }
    public String upload(File file, String uid) throws IOException{
        String name = file.getName();
        String[] exts = name.split("\\.");
        String ext = exts[exts.length-1];
        String chemin = remotePath + uid + "."+ ext;
        InputStream inputStream = new FileInputStream(file);

        if(ftpClient.storeFile(chemin, inputStream)){
            inputStream.close();
            return uid+"."+ext;
        }else{
            inputStream.close();
            return "";
        }
    }
    public void close() throws IOException{
        if(this.ftpClient.isConnected()){
            this.ftpClient.logout();
            this.ftpClient.disconnect();
        }
    }
}
