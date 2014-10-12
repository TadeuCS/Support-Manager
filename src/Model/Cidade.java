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
@Table(name = "cidade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c"),
    @NamedQuery(name = "Cidade.findByCodcidade", query = "SELECT c FROM Cidade c WHERE c.codcidade = :codcidade"),
    @NamedQuery(name = "Cidade.findByDescricao", query = "SELECT c FROM Cidade c WHERE c.descricao = :descricao"),
    @NamedQuery(name = "Cidade.findByCodibgemunicipio", query = "SELECT c FROM Cidade c WHERE c.codibgemunicipio = :codibgemunicipio")})
public class Cidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODCIDADE")
    private Integer codcidade;
    @Basic(optional = false)
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "CODIBGEMUNICIPIO", unique = true)
    private int codibgemunicipio;
    @JoinColumn(name = "CODUF", referencedColumnName = "CODUF")
    @ManyToOne(optional = false)
    private Uf coduf;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codcidade")
    private List<Endereco> enderecoList;

    public Cidade() {
    }

    public Cidade(Integer codcidade) {
        this.codcidade = codcidade;
    }

    public Cidade(Integer codcidade, String descricao, int codibgemunicipio) {
        this.codcidade = codcidade;
        this.descricao = descricao;
        this.codibgemunicipio = codibgemunicipio;
    }

    public Integer getCodcidade() {
        return codcidade;
    }

    public void setCodcidade(Integer codcidade) {
        this.codcidade = codcidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodibgemunicipio() {
        return codibgemunicipio;
    }

    public void setCodibgemunicipio(int codibgemunicipio) {
        this.codibgemunicipio = codibgemunicipio;
    }

    public Uf getCoduf() {
        return coduf;
    }

    public void setCoduf(Uf coduf) {
        this.coduf = coduf;
    }

    @XmlTransient
    public List<Endereco> getEnderecoList() {
        return enderecoList;
    }

    public void setEnderecoList(List<Endereco> enderecoList) {
        this.enderecoList = enderecoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codcidade != null ? codcidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.codcidade == null && other.codcidade != null) || (this.codcidade != null && !this.codcidade.equals(other.codcidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Cidade[ codcidade=" + codcidade + " ]";
    }
    
}
