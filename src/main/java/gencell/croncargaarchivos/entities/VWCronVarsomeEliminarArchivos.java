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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JOSEPH
 */
@Entity
@Table(name = "VWCronVarsomeEliminarArchivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findAll", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v")
    , @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findById", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v WHERE v.id = :id")
    , @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findByNombreArchivoRenombrado", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v WHERE v.nombreArchivoRenombrado = :nombreArchivoRenombrado")
    , @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findByUsuarioFTP", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v WHERE v.usuarioFTP = :usuarioFTP")
    , @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findByPasswordFTP", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v WHERE v.passwordFTP = :passwordFTP")
    , @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findByUsuarioRedLocal", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v WHERE v.usuarioRedLocal = :usuarioRedLocal")
    , @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findByPasswordRedLocal", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v WHERE v.passwordRedLocal = :passwordRedLocal")
    , @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findByIp", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v WHERE v.ip = :ip")
    , @NamedQuery(name = "VWCronVarsomeEliminarArchivos.findByIdVarsome", query = "SELECT v FROM VWCronVarsomeEliminarArchivos v WHERE v.idVarsome = :idVarsome")})
public class VWCronVarsomeEliminarArchivos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @Id
    private int id;
    @Size(max = 200)
    @Column(name = "nombreArchivoRenombrado")
    private String nombreArchivoRenombrado;
    @Size(max = 45)
    @Column(name = "usuarioFTP")
    private String usuarioFTP;
    @Size(max = 100)
    @Column(name = "passwordFTP")
    private String passwordFTP;
    @Size(max = 45)
    @Column(name = "usuarioRedLocal")
    private String usuarioRedLocal;
    @Size(max = 100)
    @Column(name = "passwordRedLocal")
    private String passwordRedLocal;
    @Size(max = 45)
    @Column(name = "ip")
    private String ip;
    @Size(max = 45)
    @Column(name = "idVarsome")
    private String idVarsome;

    public VWCronVarsomeEliminarArchivos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreArchivoRenombrado() {
        return nombreArchivoRenombrado;
    }

    public void setNombreArchivoRenombrado(String nombreArchivoRenombrado) {
        this.nombreArchivoRenombrado = nombreArchivoRenombrado;
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

    public String getIdVarsome() {
        return idVarsome;
    }

    public void setIdVarsome(String idVarsome) {
        this.idVarsome = idVarsome;
    }
    
}
