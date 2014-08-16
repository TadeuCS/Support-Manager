/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    @NamedQuery(name = "Empresa.findByCnpjCpf", query = "SELECT e FROM Empresa e WHERE e.cnpjCpf = :cnpjCpf"),
    @NamedQuery(name = "Empresa.findByTelefone", query = "SELECT e FROM Empresa e WHERE e.telefone = :telefone"),
    @NamedQuery(name = "Empresa.findByCodcontato", query = "SELECT e FROM Empresa e WHERE e.codcontato = :codcontato")})
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
    @Basic(optional = false)
    @Column(name = "TELEFONE")
    private String telefone;
    @Basic(optional = false)
    @Column(name = "CODCONTATO")
    private int codcontato;
    @JoinColumn(name = "CODTELEFONE", referencedColumnName = "CODTELEFONE")
    @ManyToOne(optional = false)
    private Telefone codtelefone;
    @JoinColumn(name = "CODEMAIL", referencedColumnName = "CODEMAIL")
    @ManyToOne(optional = false)
    private Email codemail;

    public Empresa() {
    }

    public Empresa(Integer codempresa) {
        this.codempresa = codempresa;
    }

    public Empresa(Integer codempresa, String nomeFantasia, String cnpjCpf, String telefone, int codcontato) {
        this.codempresa = codempresa;
        this.nomeFantasia = nomeFantasia;
        this.cnpjCpf = cnpjCpf;
        this.telefone = telefone;
        this.codcontato = codcontato;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getCodcontato() {
        return codcontato;
    }

    public void setCodcontato(int codcontato) {
        this.codcontato = codcontato;
    }

    public Telefone getCodtelefone() {
        return codtelefone;
    }

    public void setCodtelefone(Telefone codtelefone) {
        this.codtelefone = codtelefone;
    }

    public Email getCodemail() {
        return codemail;
    }

    public void setCodemail(Email codemail) {
        this.codemail = codemail;
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
