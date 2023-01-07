/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JOSEPH
 */
@Entity
@Table(name = "VWCronVarsomeCargaArchivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWCronVarsomeCargaArchivos.findAll", query = "SELECT v FROM VWCronVarsomeCargaArchivos v")
    , @NamedQuery(name = "VWCronVarsomeCargaArchivos.findById", query = "SELECT v FROM VWCronVarsomeCargaArchivos v WHERE v.id = :id")
    , @NamedQuery(name = "VWCronVarsomeCargaArchivos.findByUsuarioFTP", query = "SELECT v FROM VWCronVarsomeCargaArchivos v WHERE v.usuarioFTP = :usuarioFTP")
    , @NamedQuery(name = "VWCronVarsomeCargaArchivos.findByPasswordFTP", query = "SELECT v FROM VWCronVarsomeCargaArchivos v WHERE v.passwordFTP = :passwordFTP")
    , @NamedQuery(name = "VWCronVarsomeCargaArchivos.findByUsuarioRedLocal", query = "SELECT v FROM VWCronVarsomeCargaArchivos v WHERE v.usuarioRedLocal = :usuarioRedLocal")
    , @NamedQuery(name = "VWCronVarsomeCargaArchivos.findByPasswordRedLocal", query = "SELECT v FROM VWCronVarsomeCargaArchivos v WHERE v.passwordRedLocal = :passwordRedLocal")})
public class VWCronVarsomeCargaArchivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "url")
    private String url;
    @Column(name = "usuarioFTP")
    private String usuarioFTP;
    @Column(name = "passwordFTP")
    private String passwordFTP;
    @Column(name = "usuarioRedLocal")
    private String usuarioRedLocal;
    @Column(name = "passwordRedLocal")
    private String passwordRedLocal;
    @Column(name = "nombreArchivo")
    private String nombreArchivo;
    @Column(name = "nombreArchivoRenombrado")
    private String nombreArchivoRenombrado;    
    @Column(name = "ip")
    private String ip;

    public VWCronVarsomeCargaArchivos() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuarioFTP() {
        return usuarioFTP;
    }

    public void setUsuarioFTP(String usuarioFTP) {
        this.usuarioFTP = usuarioFTP;
    }

    public String getPasswordFTP() {
        return passwordFTP;
    }

    public void setPasswordFTP(String passwordFTP) {
        this.passwordFTP = passwordFTP;
    }

    public String getUsuarioRedLocal() {
        return usuarioRedLocal;
    }

    public void setUsuarioRedLocal(String usuarioRedLocal) {
        this.usuarioRedLocal = usuarioRedLocal;
    }

    public String getPasswordRedLocal() {
        return passwordRedLocal;
    }

    public void setPasswordRedLocal(String passwordRedLocal) {
        this.passwordRedLocal = passwordRedLocal;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivoRenombrado() {
        return nombreArchivoRenombrado;
    }

    public void setNombreArchivoRenombrado(String nombreArchivoRenombrado) {
        this.nombreArchivoRenombrado = nombreArchivoRenombrado;
    }
    
    
 
}
