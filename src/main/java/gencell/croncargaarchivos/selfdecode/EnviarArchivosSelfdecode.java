/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.selfdecode;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author USUARIO
 */
public class EnviarArchivosSelfdecode implements Serializable{
    
     @JsonProperty("profile")
    private String profile;
    
    @JsonProperty("forwardReads")
    private String forwardReads;
    
    @JsonProperty("reverseReads")
    private String reverseReads;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getForwardReads() {
        return forwardReads;
    }

    public void setForwardReads(String forwardReads) {
        this.forwardReads = forwardReads;
    }

    public String getReverseReads() {
        return reverseReads;
    }

    public void setReverseReads(String reverseReads) {
        this.reverseReads = reverseReads;
    }
}
