/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.selfdecode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author USUARIO
 */
public class UploadFile {
    
    @JsonProperty("filename")
    private String filename;
    
    @JsonProperty("url")
    private String url;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
   
}

