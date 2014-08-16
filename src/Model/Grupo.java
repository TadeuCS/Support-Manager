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
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByCodgrupo", query = "SELECT g FROM Grupo g WHERE g.codgrupo = :codgrupo"),
    @NamedQuery(name = "Grupo.findByDescricao", query = "SELECT g FROM Grupo g WHERE g.descricao = :descricao")})
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODGRUPO")
    private Integer codgrupo;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codgrupo")
    private List<Telefone> telefoneList;

    public Grupo() {
    }

    public Grupo(Integer codgrupo) {
        this.codgrupo = codgrupo;
    }

    public Grupo(Integer codgrupo, String descricao) {
        this.codgrupo = codgrupo;
        this.descricao = descricao;
    }

    public Integer getCodgrupo() {
        return codgrupo;
    }

    public void setCodgrupo(Integer codgrupo) {
        this.codgrupo = codgrupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Telefone> getTelefoneList() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList = telefoneList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codgrupo != null ? codgrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.codgrupo == null && other.codgrupo != null) || (this.codgrupo != null && !this.codgrupo.equals(other.codgrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Grupo[ codgrupo=" + codgrupo + " ]";
    }
    
}
