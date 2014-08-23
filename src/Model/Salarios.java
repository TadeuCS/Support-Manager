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
@Table(name = "salarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salarios.findAll", query = "SELECT s FROM Salarios s"),
    @NamedQuery(name = "Salarios.findByCodsalario", query = "SELECT s FROM Salarios s WHERE s.codsalario = :codsalario"),
    @NamedQuery(name = "Salarios.findByAno", query = "SELECT s FROM Salarios s WHERE s.ano = :ano"),
    @NamedQuery(name = "Salarios.findByValor", query = "SELECT s FROM Salarios s WHERE s.valor = :valor")})
public class Salarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODSALARIO")
    private Integer codsalario;
    @Basic(optional = false)
    @Column(name = "ANO")
    @Temporal(TemporalType.DATE)
    private Date ano;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private double valor;

    public Salarios() {
    }

    public Salarios(Integer codsalario) {
        this.codsalario = codsalario;
    }

    public Salarios(Integer codsalario, Date ano, double valor) {
        this.codsalario = codsalario;
        this.ano = ano;
        this.valor = valor;
    }

    public Integer getCodsalario() {
        return codsalario;
    }

    public void setCodsalario(Integer codsalario) {
        this.codsalario = codsalario;
    }

    public Date getAno() {
        return ano;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codsalario != null ? codsalario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salarios)) {
            return false;
        }
        Salarios other = (Salarios) object;
        if ((this.codsalario == null && other.codsalario != null) || (this.codsalario != null && !this.codsalario.equals(other.codsalario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Salarios[ codsalario=" + codsalario + " ]";
    }
    
}