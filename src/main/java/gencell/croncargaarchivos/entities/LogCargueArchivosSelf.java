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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "LogCargueArchivosSelf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogCargueArchivosSelf.findAll", query = "SELECT l FROM LogCargueArchivosSelf l")
    , @NamedQuery(name = "LogCargueArchivosSelf.findById", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.id = :id")
    , @NamedQuery(name = "LogCargueArchivosSelf.findByIdPersonas", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.idPersonas = :idPersonas")
    , @NamedQuery(name = "LogCargueArchivosSelf.findByIdPeticion", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.idPeticion = :idPeticion")
    , @NamedQuery(name = "LogCargueArchivosSelf.findByDatosEnviados", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.datosEnviados = :datosEnviados")
    , @NamedQuery(name = "LogCargueArchivosSelf.findByIdProfileSelfdecode", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.idProfileSelfdecode = :idProfileSelfdecode")
    , @NamedQuery(name = "LogCargueArchivosSelf.findByIdGenomeFile", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.idGenomeFile = :idGenomeFile")
    , @NamedQuery(name = "LogCargueArchivosSelf.findByEstado", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.estado = :estado")
    , @NamedQuery(name = "LogCargueArchivosSelf.findByFechaEnvio", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.fechaEnvio = :fechaEnvio")
    , @NamedQuery(name = "LogCargueArchivosSelf.findByFechaEstadoSelfdecode", query = "SELECT l FROM LogCargueArchivosSelf l WHERE l.fechaEstadoSelfdecode = :fechaEstadoSelfdecode")})
public class LogCargueArchivosSelf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idPersonas")
    private Integer idPersonas;
    @Column(name = "idPeticion")
    private Integer idPeticion;
    @Size(max = 1000)
    @Column(name = "datosEnviados")
    private String datosEnviados;
    @Size(max = 200)
    @Column(name = "idProfileSelfdecode")
    private String idProfileSelfdecode;
    @Size(max = 200)
    @Column(name = "idGenomeFile")
    private String idGenomeFile;
    @Size(max = 20)
    @Column(name = "estado")
    private String estado;
    @Size(max = 40)
    @Column(name = "fechaEnvio")
    private String fechaEnvio;
    @Size(max = 40)
    @Column(name = "fechaEstadoSelfdecode")
    private String fechaEstadoSelfdecode;

    public LogCargueArchivosSelf() {
    }

    public LogCargueArchivosSelf(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPersonas() {
        return idPersonas;
    }

    public void setIdPersonas(Integer idPersonas) {
        this.idPersonas = idPersonas;
    }

    public Integer getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Integer idPeticion) {
        this.idPeticion = idPeticion;
    }

    public String getDatosEnviados() {
        return datosEnviados;
    }

    public void setDatosEnviados(String datosEnviados) {
        this.datosEnviados = datosEnviados;
    }

    public String getIdProfileSelfdecode() {
        return idProfileSelfdecode;
    }

    public void setIdProfileSelfdecode(String idProfileSelfdecode) {
        this.idProfileSelfdecode = idProfileSelfdecode;
    }

    public String getIdGenomeFile() {
        return idGenomeFile;
    }

    public void setIdGenomeFile(String idGenomeFile) {
        this.idGenomeFile = idGenomeFile;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getFechaEstadoSelfdecode() {
        return fechaEstadoSelfdecode;
    }

    public void setFechaEstadoSelfdecode(String fechaEstadoSelfdecode) {
        this.fechaEstadoSelfdecode = fechaEstadoSelfdecode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogCargueArchivosSelf)) {
            return false;
        }
        LogCargueArchivosSelf other = (LogCargueArchivosSelf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gencell.croncargaarchivos.entities.LogCargueArchivosSelf[ id=" + id + " ]";
    }
    
}
