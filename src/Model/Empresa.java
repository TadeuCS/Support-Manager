/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findByCodempresa", query = "SELECT e FROM Empresa e WHERE e.codempresa = :codempresa"),
    @NamedQuery(name = "Empresa.findByNomeFantasia", query = "SELECT e FROM Empresa e WHERE e.nomeFantasia = :nomeFantasia"),
    @NamedQuery(name = "Empresa.findByCnpjCpf", query = "SELECT e FROM Empresa e WHERE e.cnpjCpf = :cnpjCpf")})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODEMPRESA")
    private Integer codempresa;
    @Basic(optional = false)
    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;
    @Basic(optional = false)
    @Column(name = "CNPJ_CPF")
    private String cnpjCpf;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "codempresa")
    private List<Telefone> telefoneList=new ArrayList<>();
    @JoinColumn(name = "CODEMAIL", referencedColumnName = "CODEMAIL")
    @ManyToOne(optional = false)
    private Email codemail;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "codempresa")
    private List<Endereco> enderecoList= new ArrayList<>();

    public Empresa() {
    }

    public Empresa(Integer codempresa) {
        this.codempresa = codempresa;
    }

    public Empresa(Integer codempresa, String nomeFantasia, String cnpjCpf) {
        this.codempresa = codempresa;
        this.nomeFantasia = nomeFantasia;
        this.cnpjCpf = cnpjCpf;
    }

    public Integer getCodempresa() {
        return codempresa;
    }

    public void setCodempresa(Integer codempresa) {
        this.codempresa = codempresa;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    @XmlTransient
    public List<Telefone> getTelefoneList() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList = telefoneList;
    }

    public Email getCodemail() {
        return codemail;
    }

    public void setCodemail(Email codemail) {
        this.codemail = codemail;
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
        hash += (codempresa != null ? codempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.codempresa == null && other.codempresa != null) || (this.codempresa != null && !this.codempresa.equals(other.codempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Empresa[ codempresa=" + codempresa + " ]";
    }
    
}
