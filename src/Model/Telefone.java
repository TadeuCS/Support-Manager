/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tadeu
 */
@Entity
@Table(name = "telefone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telefone.findAll", query = "SELECT t FROM Telefone t"),
    @NamedQuery(name = "Telefone.findByCodtelefone", query = "SELECT t FROM Telefone t WHERE t.codtelefone = :codtelefone"),
    @NamedQuery(name = "Telefone.findByDescricao", query = "SELECT t FROM Telefone t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "Telefone.findByTelefone", query = "SELECT t FROM Telefone t WHERE t.telefone = :telefone")})
public class Telefone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODTELEFONE")
    private Integer codtelefone;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "TELEFONE")
    private String telefone;
    @JoinColumn(name = "CODGRUPO", referencedColumnName = "CODGRUPO")
    @ManyToOne(optional = false)
    private Grupo codgrupo;
    @JoinColumn(name = "CODUSUARIO", referencedColumnName = "CODUSUARIO")
    @ManyToOne
    private Usuario codusuario;
    @JoinColumn(name = "CODCLIENTE", referencedColumnName = "CODCLIENTE")
    @ManyToOne
    private Cliente codcliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codtelefone")
    private List<Empresa> empresaList;

    public Telefone() {
    }

    public Telefone(Integer codtelefone) {
        this.codtelefone = codtelefone;
    }

    public Telefone(Integer codtelefone, String descricao, String telefone) {
        this.codtelefone = codtelefone;
        this.descricao = descricao;
        this.telefone = telefone;
    }

    public Integer getCodtelefone() {
        return codtelefone;
    }

    public void setCodtelefone(Integer codtelefone) {
        this.codtelefone = codtelefone;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Grupo getCodgrupo() {
        return codgrupo;
    }

    public void setCodgrupo(Grupo codgrupo) {
        this.codgrupo = codgrupo;
    }

    public Usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Usuario codusuario) {
        this.codusuario = codusuario;
    }

    public Cliente getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(Cliente codcliente) {
        this.codcliente = codcliente;
    }

    @XmlTransient
    public List<Empresa> getEmpresaList() {
        return empresaList;
    }

    public void setEmpresaList(List<Empresa> empresaList) {
        this.empresaList = empresaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codtelefone != null ? codtelefone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telefone)) {
            return false;
        }
        Telefone other = (Telefone) object;
        if ((this.codtelefone == null && other.codtelefone != null) || (this.codtelefone != null && !this.codtelefone.equals(other.codtelefone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Telefone[ codtelefone=" + codtelefone + " ]";
    }
    
}
