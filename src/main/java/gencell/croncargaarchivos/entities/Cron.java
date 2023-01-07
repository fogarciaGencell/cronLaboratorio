/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JOSEPH
 */
@Entity
@Table(name = "Cron")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cron.findAll", query = "SELECT c FROM Cron c")
    , @NamedQuery(name = "Cron.findByIdCron", query = "SELECT c FROM Cron c WHERE c.idCron = :idCron")
    , @NamedQuery(name = "Cron.findByCron", query = "SELECT c FROM Cron c WHERE c.cron = :cron")
    , @NamedQuery(name = "Cron.findByUrl", query = "SELECT c FROM Cron c WHERE c.url = :url")
    , @NamedQuery(name = "Cron.findByFechaInicio", query = "SELECT c FROM Cron c WHERE c.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Cron.findByFechaFinal", query = "SELECT c FROM Cron c WHERE c.fechaFinal = :fechaFinal")
    , @NamedQuery(name = "Cron.findByDescripcion", query = "SELECT c FROM Cron c WHERE c.descripcion = :descripcion")})
public class Cron implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCron")
    private Integer idCron;    
    @Column(name = "cron")
    private String cron;    
    @Column(name = "url")
    private String url;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fechaFinal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;    
    @Column(name = "descripcion")
    private String descripcion;

    public Cron() {
    }

    public Cron(Integer idCron) {
        this.idCron = idCron;
    }

    public Cron(Integer idCron, String cron, String url) {
        this.idCron = idCron;
        this.cron = cron;
        this.url = url;
    }

    public Integer getIdCron() {
        return idCron;
    }

    public void setIdCron(Integer idCron) {
        this.idCron = idCron;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCron != null ? idCron.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cron)) {
            return false;
        }
        Cron other = (Cron) object;
        if ((this.idCron == null && other.idCron != null) || (this.idCron != null && !this.idCron.equals(other.idCron))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gencell.croncargaarchivos.entities.Cron[ idCron=" + idCron + " ]";
    }
    
}
