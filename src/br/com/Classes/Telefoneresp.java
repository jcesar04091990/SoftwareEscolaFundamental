/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Classes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "telefoneresp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telefoneresp.findAll", query = "SELECT t FROM Telefoneresp t"),
    @NamedQuery(name = "Telefoneresp.findByIdTelResp", query = "SELECT t FROM Telefoneresp t WHERE t.idTelResp = :idTelResp")})
public class Telefoneresp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idTelResp")
    private Integer idTelResp;
    @JoinColumn(name = "idResponsavel", referencedColumnName = "idResponsavel")
    @ManyToOne
    private Responsavel idResponsavel;

    public Telefoneresp() {
    }

    public Telefoneresp(Integer idTelResp) {
        this.idTelResp = idTelResp;
    }

    public Integer getIdTelResp() {
        return idTelResp;
    }

    public void setIdTelResp(Integer idTelResp) {
        this.idTelResp = idTelResp;
    }

    public Responsavel getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Responsavel idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelResp != null ? idTelResp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telefoneresp)) {
            return false;
        }
        Telefoneresp other = (Telefoneresp) object;
        if ((this.idTelResp == null && other.idTelResp != null) || (this.idTelResp != null && !this.idTelResp.equals(other.idTelResp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Telefoneresp[ idTelResp=" + idTelResp + " ]";
    }
    
}
