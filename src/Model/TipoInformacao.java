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
@Table(name = "tipo_informacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoInformacao.findAll", query = "SELECT t FROM TipoInformacao t"),
    @NamedQuery(name = "TipoInformacao.findByCodtipoinformacao", query = "SELECT t FROM TipoInformacao t WHERE t.codtipoinformacao = :codtipoinformacao"),
    @NamedQuery(name = "TipoInformacao.findByDescricao", query = "SELECT t FROM TipoInformacao t WHERE t.descricao = :descricao")})
public class TipoInformacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODTIPOINFORMACAO")
    private Integer codtipoinformacao;
    @Basic(optional = false)
    @Column(name = "DESCRICAO",unique = true)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codtipoinformacao")
    private List<Informacao> informacaoList;

    public TipoInformacao() {
    }

    public TipoInformacao(Integer codtipoinformacao) {
        this.codtipoinformacao = codtipoinformacao;
    }

    public TipoInformacao(Integer codtipoinformacao, String descricao) {
        this.codtipoinformacao = codtipoinformacao;
        this.descricao = descricao;
    }

    public Integer getCodtipoinformacao() {
        return codtipoinformacao;
    }

    public void setCodtipoinformacao(Integer codtipoinformacao) {
        this.codtipoinformacao = codtipoinformacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Informacao> getInformacaoList() {
        return informacaoList;
    }

    public void setInformacaoList(List<Informacao> informacaoList) {
        this.informacaoList = informacaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codtipoinformacao != null ? codtipoinformacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoInformacao)) {
            return false;
        }
        TipoInformacao other = (TipoInformacao) object;
        if ((this.codtipoinformacao == null && other.codtipoinformacao != null) || (this.codtipoinformacao != null && !this.codtipoinformacao.equals(other.codtipoinformacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.TipoInformacao[ codtipoinformacao=" + codtipoinformacao + " ]";
    }
    
}
