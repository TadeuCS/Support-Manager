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

@Entity
@Table(name = "link")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Link.findAll", query = "SELECT l FROM Link l"),
    @NamedQuery(name = "Link.findByCodlink", query = "SELECT l FROM Link l WHERE l.codlink = :codlink"),
    @NamedQuery(name = "Link.findByDescricao", query = "SELECT l FROM Link l WHERE l.descricao = :descricao")})
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODLINK")
    private Integer codlink;
    @Basic(optional = false)
    @Column(name = "DESCRICAO",unique = true)
    private String descricao;
    @JoinColumn(name = "CODAPLICATIVO", referencedColumnName = "CODAPLICATIVO")
    @ManyToOne(optional = false)
    private Aplicativo codaplicativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codLink")
    private List<LinkCliente> linkClienteList;

    public Link() {
    }

    public Link(Integer codlink) {
        this.codlink = codlink;
    }

    public Link(Integer codlink, String descricao) {
        this.codlink = codlink;
        this.descricao = descricao;
    }

    public Integer getCodlink() {
        return codlink;
    }

    public void setCodlink(Integer codlink) {
        this.codlink = codlink;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Aplicativo getCodaplicativo() {
        return codaplicativo;
    }

    public void setCodaplicativo(Aplicativo codaplicativo) {
        this.codaplicativo = codaplicativo;
    }

    @XmlTransient
    public List<LinkCliente> getLinkClienteList() {
        return linkClienteList;
    }

    public void setLinkClienteList(List<LinkCliente> linkClienteList) {
        this.linkClienteList = linkClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codlink != null ? codlink.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Link)) {
            return false;
        }
        Link other = (Link) object;
        if ((this.codlink == null && other.codlink != null) || (this.codlink != null && !this.codlink.equals(other.codlink))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Link[ codlink=" + codlink + " ]";
    }
    
}
