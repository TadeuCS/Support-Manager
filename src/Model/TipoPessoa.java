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
@Table(name = "tipo_pessoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPessoa.findAll", query = "SELECT t FROM TipoPessoa t"),
    @NamedQuery(name = "TipoPessoa.findByCodtipopessoa", query = "SELECT t FROM TipoPessoa t WHERE t.codtipopessoa = :codtipopessoa"),
    @NamedQuery(name = "TipoPessoa.findByDescricao", query = "SELECT t FROM TipoPessoa t WHERE t.descricao = :descricao")})
public class TipoPessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODTIPOPESSOA")
    private Integer codtipopessoa;
    @Basic(optional = false)
    @Column(name = "DESCRICAO",unique = true)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codtipopessoa")
    private List<Cliente> clienteList;

    public TipoPessoa() {
    }

    public TipoPessoa(Integer codtipopessoa) {
        this.codtipopessoa = codtipopessoa;
    }

    public TipoPessoa(Integer codtipopessoa, String descricao) {
        this.codtipopessoa = codtipopessoa;
        this.descricao = descricao;
    }

    public Integer getCodtipopessoa() {
        return codtipopessoa;
    }

    public void setCodtipopessoa(Integer codtipopessoa) {
        this.codtipopessoa = codtipopessoa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codtipopessoa != null ? codtipopessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPessoa)) {
            return false;
        }
        TipoPessoa other = (TipoPessoa) object;
        if ((this.codtipopessoa == null && other.codtipopessoa != null) || (this.codtipopessoa != null && !this.codtipopessoa.equals(other.codtipopessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.TipoPessoa[ codtipopessoa=" + codtipopessoa + " ]";
    }
    
}
