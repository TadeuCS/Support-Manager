/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tadeu
 */
@Entity
@Table(name = "ponto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ponto.findAll", query = "SELECT p FROM Ponto p"),
    @NamedQuery(name = "Ponto.findByCodponto", query = "SELECT p FROM Ponto p WHERE p.codponto = :codponto"),
    @NamedQuery(name = "Ponto.findByTipo", query = "SELECT p FROM Ponto p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Ponto.findByObservacao", query = "SELECT p FROM Ponto p WHERE p.observacao = :observacao"),
    @NamedQuery(name = "Ponto.findByDataEntrada", query = "SELECT p FROM Ponto p WHERE p.dataEntrada = :dataEntrada"),
    @NamedQuery(name = "Ponto.findByDataSaida", query = "SELECT p FROM Ponto p WHERE p.dataSaida = :dataSaida"),
    @NamedQuery(name = "Ponto.findByDataAlmocoEntrada", query = "SELECT p FROM Ponto p WHERE p.dataAlmocoEntrada = :dataAlmocoEntrada"),
    @NamedQuery(name = "Ponto.findByDataAlmocoSaida", query = "SELECT p FROM Ponto p WHERE p.dataAlmocoSaida = :dataAlmocoSaida")})
public class Ponto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODPONTO")
    private Integer codponto;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private Character tipo;
    @Column(name = "OBSERVACAO")
    private String observacao;
    @Basic(optional = false)
    @Column(name = "DATA_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntrada;
    @Column(name = "DATA_SAIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSaida;
    @Column(name = "DATA_ALMOCO_ENTRADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlmocoEntrada;
    @Column(name = "DATA_ALMOCO_SAIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlmocoSaida;
    @JoinColumn(name = "CODUSUARIO", referencedColumnName = "CODUSUARIO")
    @ManyToOne(optional = false)
    private Usuario codusuario;

    public Ponto() {
    }

    public Ponto(Integer codponto) {
        this.codponto = codponto;
    }

    public Ponto(Integer codponto, Character tipo, Date dataEntrada) {
        this.codponto = codponto;
        this.tipo = tipo;
        this.dataEntrada = dataEntrada;
    }

    public Integer getCodponto() {
        return codponto;
    }

    public void setCodponto(Integer codponto) {
        this.codponto = codponto;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Date getDataAlmocoEntrada() {
        return dataAlmocoEntrada;
    }

    public void setDataAlmocoEntrada(Date dataAlmocoEntrada) {
        this.dataAlmocoEntrada = dataAlmocoEntrada;
    }

    public Date getDataAlmocoSaida() {
        return dataAlmocoSaida;
    }

    public void setDataAlmocoSaida(Date dataAlmocoSaida) {
        this.dataAlmocoSaida = dataAlmocoSaida;
    }

    public Usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Usuario codusuario) {
        this.codusuario = codusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codponto != null ? codponto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ponto)) {
            return false;
        }
        Ponto other = (Ponto) object;
        if ((this.codponto == null && other.codponto != null) || (this.codponto != null && !this.codponto.equals(other.codponto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Ponto[ codponto=" + codponto + " ]";
    }
    
}
