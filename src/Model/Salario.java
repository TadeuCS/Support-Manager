package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "salario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salario.findAll", query = "SELECT s FROM Salario s"),
    @NamedQuery(name = "Salario.findByCodsalario", query = "SELECT s FROM Salario s WHERE s.codsalario = :codsalario"),
    @NamedQuery(name = "Salario.findByAno", query = "SELECT MAX(s.valor) FROM Salario s WHERE s.ano = :ano"),
    @NamedQuery(name = "Salario.findByValor", query = "SELECT s FROM Salario s WHERE s.valor = :valor")})
public class Salario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODSALARIO")
    private Integer codsalario;
    @Basic(optional = false)
    @Column(name = "ANO")
    private int ano;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private double valor;

    public Salario() {
    }

    public Salario(Integer codsalario) {
        this.codsalario = codsalario;
    }

    public Salario(Integer codsalario, int ano, double valor) {
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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
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
        if (!(object instanceof Salario)) {
            return false;
        }
        Salario other = (Salario) object;
        if ((this.codsalario == null && other.codsalario != null) || (this.codsalario != null && !this.codsalario.equals(other.codsalario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Salario[ codsalario=" + codsalario + " ]";
    }
    
}
