package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "endereco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e"),
    @NamedQuery(name = "Endereco.findByCodendereco", query = "SELECT e FROM Endereco e WHERE e.codendereco = :codendereco"),
    @NamedQuery(name = "Endereco.findByNumero", query = "SELECT e FROM Endereco e WHERE e.numero = :numero"),
    @NamedQuery(name = "Endereco.findByComplemento", query = "SELECT e FROM Endereco e WHERE e.complemento = :complemento"),
    @NamedQuery(name = "Endereco.findByBairro", query = "SELECT e FROM Endereco e WHERE e.bairro = :bairro"),
    @NamedQuery(name = "Endereco.findByRua", query = "SELECT e FROM Endereco e WHERE e.rua = :rua"),
    @NamedQuery(name = "Endereco.findByCep", query = "SELECT e FROM Endereco e WHERE e.cep = :cep GROUP BY e.cep")})
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODENDERECO")
    private Integer codendereco;
    @Basic(optional = false)
    @Column(name = "NUMERO")
    private int numero;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Basic(optional = false)
    @Column(name = "BAIRRO")
    private String bairro;
    @Basic(optional = false)
    @Column(name = "RUA")
    private String rua;
    @Basic(optional = false)
    @Column(name = "CEP")
    private String cep;
    @JoinColumn(name = "CODCIDADE", referencedColumnName = "CODCIDADE")
    @ManyToOne(optional = false)
    private Cidade codcidade;
    @JoinColumn(name = "CODCLIENTE", referencedColumnName = "CODCLIENTE")
    @ManyToOne
    private Cliente codcliente;
    @JoinColumn(name = "CODEMPRESA", referencedColumnName = "CODEMPRESA")
    @ManyToOne
    private Empresa codempresa;

    public Endereco() {
    }

    public Endereco(Integer codendereco) {
        this.codendereco = codendereco;
    }

    public Endereco(Integer codendereco, int numero, String bairro, String rua, String cep) {
        this.codendereco = codendereco;
        this.numero = numero;
        this.bairro = bairro;
        this.rua = rua;
        this.cep = cep;
    }

    public Integer getCodendereco() {
        return codendereco;
    }

    public void setCodendereco(Integer codendereco) {
        this.codendereco = codendereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCodcidade() {
        return codcidade;
    }

    public void setCodcidade(Cidade codcidade) {
        this.codcidade = codcidade;
    }

    public Cliente getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(Cliente codcliente) {
        this.codcliente = codcliente;
    }

    public Empresa getCodempresa() {
        return codempresa;
    }

    public void setCodempresa(Empresa codempresa) {
        this.codempresa = codempresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codendereco != null ? codendereco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        if ((this.codendereco == null && other.codendereco != null) || (this.codendereco != null && !this.codendereco.equals(other.codendereco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Endereco[ codendereco=" + codendereco + " ]";
    }
    
}
