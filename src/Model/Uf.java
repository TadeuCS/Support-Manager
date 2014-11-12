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

@Entity
@Table(name = "uf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uf.findAll", query = "SELECT u FROM Uf u order by u.sigla"),
    @NamedQuery(name = "Uf.findByCoduf", query = "SELECT u FROM Uf u WHERE u.coduf = :coduf"),
    @NamedQuery(name = "Uf.findByDescricao", query = "SELECT u FROM Uf u WHERE u.descricao = :descricao"),
    @NamedQuery(name = "Uf.findBySigla", query = "SELECT u FROM Uf u WHERE u.sigla = :sigla")})
public class Uf implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODUF")
    private Integer coduf;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "SIGLA",unique = true)
    private String sigla;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coduf")
    private List<Cidade> cidadeList;

    public Uf() {
    }

    public Uf(Integer coduf) {
        this.coduf = coduf;
    }

    public Uf(Integer coduf, String descricao, String sigla) {
        this.coduf = coduf;
        this.descricao = descricao;
        this.sigla = sigla;
    }

    public Integer getCoduf() {
        return coduf;
    }

    public void setCoduf(Integer coduf) {
        this.coduf = coduf;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @XmlTransient
    public List<Cidade> getCidadeList() {
        return cidadeList;
    }

    public void setCidadeList(List<Cidade> cidadeList) {
        this.cidadeList = cidadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coduf != null ? coduf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uf)) {
            return false;
        }
        Uf other = (Uf) object;
        if ((this.coduf == null && other.coduf != null) || (this.coduf != null && !this.coduf.equals(other.coduf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Uf[ coduf=" + coduf + " ]";
    }
    
}
