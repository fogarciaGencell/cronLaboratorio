/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gencell.croncargaarchivos.selfdecode;

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
 * @author USUARIO
 */
@Entity
@Table(name = "ProfilePersonaSelfdecode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProfilePersonaSelfdecode.findAll", query = "SELECT p FROM ProfilePersonaSelfdecode p")
    , @NamedQuery(name = "ProfilePersonaSelfdecode.findByIdPaciente", query = "SELECT p FROM ProfilePersonaSelfdecode p WHERE p.idPaciente = :idPaciente")
    , @NamedQuery(name = "ProfilePersonaSelfdecode.findByIdProfileSelfdecode", query = "SELECT p FROM ProfilePersonaSelfdecode p WHERE p.idProfileSelfdecode = :idProfileSelfdecode")})
public class ProfilePersonaSelfdecode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPaciente")
    private Integer idPaciente;
    @Size(max = 200)
    @Column(name = "idProfileSelfdecode")
    private String idProfileSelfdecode;

    public ProfilePersonaSelfdecode() {
    }

    public ProfilePersonaSelfdecode(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdProfileSelfdecode() {
        return idProfileSelfdecode;
    }

    public void setIdProfileSelfdecode(String idProfileSelfdecode) {
        this.idProfileSelfdecode = idProfileSelfdecode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfilePersonaSelfdecode)) {
            return false;
        }
        ProfilePersonaSelfdecode other = (ProfilePersonaSelfdecode) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gencell.croncargararchivos.dao.ProfilePersonaSelfdecode[ idPaciente=" + idPaciente + " ]";
    }
    
}
