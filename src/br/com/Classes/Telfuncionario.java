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
@Table(name = "telfuncionario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telfuncionario.findAll", query = "SELECT t FROM Telfuncionario t"),
    @NamedQuery(name = "Telfuncionario.findByIdTelFunc", query = "SELECT t FROM Telfuncionario t WHERE t.idTelFunc = :idTelFunc")})
public class Telfuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idTelFunc")
    private Integer idTelFunc;
    @JoinColumn(name = "codFunc", referencedColumnName = "codFunc")
    @ManyToOne
    private Funcionario codFunc;

    public Telfuncionario() {
    }

    public Telfuncionario(Integer idTelFunc) {
        this.idTelFunc = idTelFunc;
    }

    public Integer getIdTelFunc() {
        return idTelFunc;
    }

    public void setIdTelFunc(Integer idTelFunc) {
        this.idTelFunc = idTelFunc;
    }

    public Funcionario getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(Funcionario codFunc) {
        this.codFunc = codFunc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelFunc != null ? idTelFunc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telfuncionario)) {
            return false;
        }
        Telfuncionario other = (Telfuncionario) object;
        if ((this.idTelFunc == null && other.idTelFunc != null) || (this.idTelFunc != null && !this.idTelFunc.equals(other.idTelFunc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Telfuncionario[ idTelFunc=" + idTelFunc + " ]";
    }
    
}
