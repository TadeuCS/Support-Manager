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
@Table(name = "origem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Origem.findAll", query = "SELECT o FROM Origem o"),
    @NamedQuery(name = "Origem.findByCodorigem", query = "SELECT o FROM Origem o WHERE o.codorigem = :codorigem"),
    @NamedQuery(name = "Origem.findByDescricao", query = "SELECT o FROM Origem o WHERE o.descricao = :descricao")})
public class Origem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODORIGEM")
    private Integer codorigem;
    @Basic(optional = false)
    @Column(name = "DESCRICAO",unique = true)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codorigem")
    private List<Atendimento> atendimentoList;

    public Origem() {
    }

    public Origem(Integer codorigem) {
        this.codorigem = codorigem;
    }

    public Origem(Integer codorigem, String descricao) {
        this.codorigem = codorigem;
        this.descricao = descricao;
    }

    public Integer getCodorigem() {
        return codorigem;
    }

    public void setCodorigem(Integer codorigem) {
        this.codorigem = codorigem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Atendimento> getAtendimentoList() {
        return atendimentoList;
    }

    public void setAtendimentoList(List<Atendimento> atendimentoList) {
        this.atendimentoList = atendimentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codorigem != null ? codorigem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Origem)) {
            return false;
        }
        Origem other = (Origem) object;
        if ((this.codorigem == null && other.codorigem != null) || (this.codorigem != null && !this.codorigem.equals(other.codorigem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Origem[ codorigem=" + codorigem + " ]";
    }
    
}
