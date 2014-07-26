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
@Table(name = "aplicativo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aplicativo.findAll", query = "SELECT a FROM Aplicativo a"),
    @NamedQuery(name = "Aplicativo.findByCodaplicativo", query = "SELECT a FROM Aplicativo a WHERE a.codaplicativo = :codaplicativo"),
    @NamedQuery(name = "Aplicativo.findByDescricao", query = "SELECT a FROM Aplicativo a WHERE a.descricao = :descricao")})
public class Aplicativo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODAPLICATIVO")
    private Integer codaplicativo;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codaplicativo")
    private List<Link> linkList;

    public Aplicativo() {
    }

    public Aplicativo(Integer codaplicativo) {
        this.codaplicativo = codaplicativo;
    }

    public Aplicativo(Integer codaplicativo, String descricao) {
        this.codaplicativo = codaplicativo;
        this.descricao = descricao;
    }

    public Integer getCodaplicativo() {
        return codaplicativo;
    }

    public void setCodaplicativo(Integer codaplicativo) {
        this.codaplicativo = codaplicativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codaplicativo != null ? codaplicativo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aplicativo)) {
            return false;
        }
        Aplicativo other = (Aplicativo) object;
        if ((this.codaplicativo == null && other.codaplicativo != null) || (this.codaplicativo != null && !this.codaplicativo.equals(other.codaplicativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Aplicativo[ codaplicativo=" + codaplicativo + " ]";
    }
    
}
