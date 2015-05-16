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
@Table(name = "colaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colaborador.findAll", query = "SELECT c FROM Colaborador c"),
    @NamedQuery(name = "Colaborador.findByIdColaborador", query = "SELECT c FROM Colaborador c WHERE c.idColaborador = :idColaborador"),
    @NamedQuery(name = "Colaborador.findByCargo", query = "SELECT c FROM Colaborador c WHERE c.cargo = :cargo")})
public class Colaborador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idColaborador")
    private Integer idColaborador;
    @Column(name = "cargo")
    private String cargo;
    @JoinColumn(name = "codFunc", referencedColumnName = "codFunc")
    @ManyToOne
    private Funcionario codFunc;

    public Colaborador() {
    }

    public Colaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
        hash += (idColaborador != null ? idColaborador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colaborador)) {
            return false;
        }
        Colaborador other = (Colaborador) object;
        if ((this.idColaborador == null && other.idColaborador != null) || (this.idColaborador != null && !this.idColaborador.equals(other.idColaborador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Colaborador[ idColaborador=" + idColaborador + " ]";
    }
    
}
