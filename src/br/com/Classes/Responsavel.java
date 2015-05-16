/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Classes;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "responsavel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Responsavel.findAll", query = "SELECT r FROM Responsavel r"),
    @NamedQuery(name = "Responsavel.findByIdResponsavel", query = "SELECT r FROM Responsavel r WHERE r.idResponsavel = :idResponsavel"),
    @NamedQuery(name = "Responsavel.findByNome", query = "SELECT r FROM Responsavel r WHERE r.nome = :nome"),
    @NamedQuery(name = "Responsavel.findByRg", query = "SELECT r FROM Responsavel r WHERE r.rg = :rg"),
    @NamedQuery(name = "Responsavel.findByCpf", query = "SELECT r FROM Responsavel r WHERE r.cpf = :cpf"),
    @NamedQuery(name = "Responsavel.findByEmail", query = "SELECT r FROM Responsavel r WHERE r.email = :email"),
    @NamedQuery(name = "Responsavel.findByDataNasc", query = "SELECT r FROM Responsavel r WHERE r.dataNasc = :dataNasc"),
    @NamedQuery(name = "Responsavel.findByEndereco", query = "SELECT r FROM Responsavel r WHERE r.endereco = :endereco"),
    @NamedQuery(name = "Responsavel.findBySetor", query = "SELECT r FROM Responsavel r WHERE r.setor = :setor")})
public class Responsavel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idResponsavel")
    private Integer idResponsavel;
    @Column(name = "nome")
    private String nome;
    @Column(name = "rg")
    private String rg;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "email")
    private String email;
    @Column(name = "dataNasc")
    @Temporal(TemporalType.DATE)
    private Date dataNasc;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "setor")
    private String setor;
    @OneToMany(mappedBy = "idResponsavel")
    private Collection<Telefoneresp> telefonerespCollection;

    public Responsavel() {
    }

    public Responsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @XmlTransient
    public Collection<Telefoneresp> getTelefonerespCollection() {
        return telefonerespCollection;
    }

    public void setTelefonerespCollection(Collection<Telefoneresp> telefonerespCollection) {
        this.telefonerespCollection = telefonerespCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResponsavel != null ? idResponsavel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Responsavel)) {
            return false;
        }
        Responsavel other = (Responsavel) object;
        if ((this.idResponsavel == null && other.idResponsavel != null) || (this.idResponsavel != null && !this.idResponsavel.equals(other.idResponsavel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.Responsavel[ idResponsavel=" + idResponsavel + " ]";
    }
    
}
