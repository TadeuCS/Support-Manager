/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tadeu
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCodcliente", query = "SELECT c FROM Cliente c WHERE c.codcliente = :codcliente"),
    @NamedQuery(name = "Cliente.findByReferencia", query = "SELECT c FROM Cliente c WHERE c.referencia = :referencia"),
    @NamedQuery(name = "Cliente.findByNomeFantasia", query = "SELECT c FROM Cliente c WHERE c.nomeFantasia = :nomeFantasia"),
    @NamedQuery(name = "Cliente.findByRazaoSocial", query = "SELECT c FROM Cliente c WHERE c.razaoSocial = :razaoSocial"),
    @NamedQuery(name = "Cliente.findByCnpjCpf", query = "SELECT c FROM Cliente c WHERE c.cnpjCpf = :cnpjCpf"),
    @NamedQuery(name = "Cliente.findByInscricaoEstadual", query = "SELECT c FROM Cliente c WHERE c.inscricaoEstadual = :inscricaoEstadual"),
    @NamedQuery(name = "Cliente.findByResponsavel", query = "SELECT c FROM Cliente c WHERE c.responsavel = :responsavel"),
    @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email"),
    @NamedQuery(name = "Cliente.findByDataAtualizacao", query = "SELECT c FROM Cliente c WHERE c.dataAtualizacao = :dataAtualizacao")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODCLIENTE")
    private Integer codcliente;
    @Column(name = "REFERENCIA",nullable = true)
    private Integer referencia;
    @Basic(optional = false)
    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;
    @Basic(optional = false)
    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;
    @Basic(optional = false)
    @Column(name = "CNPJ_CPF")
    private String cnpjCpf;
    @Basic(optional = false)
    @Column(name = "INSCRICAO_ESTADUAL")
    private String inscricaoEstadual;
    @Basic(optional = false)
    @Column(name = "RESPONSAVEL")
    private String responsavel;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DATA_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codcliente")
    private List<Atendimento> atendimentoList;
    @JoinColumn(name = "CODTIPOPESSOA", referencedColumnName = "CODTIPOPESSOA")
    @ManyToOne(optional = false)
    private TipoPessoa codtipopessoa;
    @JoinColumn(name = "CODSEGMENTO", referencedColumnName = "CODSEGMENTO")
    @ManyToOne(optional = false)
    private Segmento codsegmento;
    @JoinColumn(name = "CODSTATUSPESSOA", referencedColumnName = "CODSTATUSPESSOA")
    @ManyToOne(optional = false)
    private StatusPessoa codstatuspessoa;
    @JoinColumn(name = "CODPARCELA", referencedColumnName = "CODPARCELA")
    @ManyToOne(optional = false)
    private Parcela codparcela;
    @OneToMany(mappedBy = "codcliente")
    private List<Telefone> telefoneList=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<LinkCliente> linkClienteList=new ArrayList<>();
    @OneToMany(mappedBy = "codcliente")
    private List<Endereco> enderecoList=new ArrayList<>();;

    public Cliente() {
    }

    public Cliente(Integer codcliente) {
        this.codcliente = codcliente;
    }

    public Cliente(Integer codcliente, String nomeFantasia, String razaoSocial, String cnpjCpf, String inscricaoEstadual, String responsavel, String email) {
        this.codcliente = codcliente;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpjCpf = cnpjCpf;
        this.inscricaoEstadual = inscricaoEstadual;
        this.responsavel = responsavel;
        this.email = email;
    }

    public Integer getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(Integer codcliente) {
        this.codcliente = codcliente;
    }

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @XmlTransient
    public List<Atendimento> getAtendimentoList() {
        return atendimentoList;
    }

    public void setAtendimentoList(List<Atendimento> atendimentoList) {
        this.atendimentoList = atendimentoList;
    }

    public TipoPessoa getCodtipopessoa() {
        return codtipopessoa;
    }

    public void setCodtipopessoa(TipoPessoa codtipopessoa) {
        this.codtipopessoa = codtipopessoa;
    }

    public Segmento getCodsegmento() {
        return codsegmento;
    }

    public void setCodsegmento(Segmento codsegmento) {
        this.codsegmento = codsegmento;
    }

    public StatusPessoa getCodstatuspessoa() {
        return codstatuspessoa;
    }

    public void setCodstatuspessoa(StatusPessoa codstatuspessoa) {
        this.codstatuspessoa = codstatuspessoa;
    }

    public Parcela getCodparcela() {
        return codparcela;
    }

    public void setCodparcela(Parcela codparcela) {
        this.codparcela = codparcela;
    }

    @XmlTransient
    public List<Telefone> getTelefoneList() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList = telefoneList;
    }

    @XmlTransient
    public List<LinkCliente> getLinkClienteList() {
        return linkClienteList;
    }

    public void setLinkClienteList(List<LinkCliente> linkClienteList) {
        this.linkClienteList = linkClienteList;
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
        hash += (codcliente != null ? codcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codcliente == null && other.codcliente != null) || (this.codcliente != null && !this.codcliente.equals(other.codcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Cliente[ codcliente=" + codcliente + " ]";
    }
    
}
