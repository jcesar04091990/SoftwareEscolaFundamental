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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "estadocivil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadocivil.findAll", query = "SELECT e FROM Estadocivil e"),
    @NamedQuery(name = "Estadocivil.findByIdEstCivil", query = "SELECT e FROM Estadocivil e WHERE e.idEstCivil = :idEstCivil"),
    @NamedQuery(name = "Estadocivil.findByDescricao", query = "SELECT e FROM Estadocivil e WHERE e.descricao = :descricao")})
public class Estadocivil implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idEstCivil")
    private Integer idEstCivil;
    @Column(name = "descricao")
    private String descricao;

    public Estadocivil() {
    }

    public Estadocivil(Integer idEstCivil) {
        this.idEstCivil = idEstCivil;
    }

    public Integer getIdEstCivil() {
        return idEstCivil;
    }

    public void setIdEstCivil(Integer idEstCivil) {
        this.idEstCivil = idEstCivil;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstCivil != null ? idEstCivil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadocivil)) {
            return false;
        }
        Estadocivil other = (Estadocivil) object;
        if ((this.idEstCivil == null && other.idEstCivil != null) || (this.idEstCivil != null && !this.idEstCivil.equals(other.idEstCivil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Estadocivil[ idEstCivil=" + idEstCivil + " ]";
    }
    
}
