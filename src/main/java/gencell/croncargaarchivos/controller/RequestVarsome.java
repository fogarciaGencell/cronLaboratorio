/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author JOSEPH
 */
public class RequestVarsome {

    @JsonProperty("sample_file_name")
    private String sample_file_name;
    @JsonProperty("file_url")
    private String file_url;

    public RequestVarsome() {
    }       
    
    public String getSample_file_name() {
        return sample_file_name;
    }

    public void setSample_file_name(String sample_file_name) {
        this.sample_file_name = sample_file_name;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }  
    
    
}
