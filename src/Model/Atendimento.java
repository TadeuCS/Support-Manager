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
@Table(name = "atendimento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atendimento.findAll", query = "SELECT a FROM Atendimento a"),
    @NamedQuery(name = "Atendimento.findByCodatendimento", query = "SELECT a FROM Atendimento a WHERE a.codatendimento = :codatendimento"),
    @NamedQuery(name = "Atendimento.findByDataAbertura", query = "SELECT a FROM Atendimento a WHERE a.dataAbertura = :dataAbertura"),
    @NamedQuery(name = "Atendimento.findByDataAgendamento", query = "SELECT a FROM Atendimento a WHERE a.dataAgendamento = :dataAgendamento"),
    @NamedQuery(name = "Atendimento.findByDataFechamento", query = "SELECT a FROM Atendimento a WHERE a.dataFechamento = :dataFechamento"),
    @NamedQuery(name = "Atendimento.findByDataCancelamento", query = "SELECT a FROM Atendimento a WHERE a.dataCancelamento = :dataCancelamento"),
    @NamedQuery(name = "Atendimento.findByDataInicio", query = "SELECT a FROM Atendimento a WHERE a.dataInicio = :dataInicio"),
    @NamedQuery(name = "Atendimento.findByDataFim", query = "SELECT a FROM Atendimento a WHERE a.dataFim = :dataFim"),
    @NamedQuery(name = "Atendimento.findBySolicitante", query = "SELECT a FROM Atendimento a WHERE a.solicitante = :solicitante"),
    @NamedQuery(name = "Atendimento.findByProblemaInformado", query = "SELECT a FROM Atendimento a WHERE a.problemaInformado = :problemaInformado"),
    @NamedQuery(name = "Atendimento.findByProblemaDetectado", query = "SELECT a FROM Atendimento a WHERE a.problemaDetectado = :problemaDetectado"),
    @NamedQuery(name = "Atendimento.findByProblemaPendencia", query = "SELECT a FROM Atendimento a WHERE a.problemaPendencia = :problemaPendencia"),
    @NamedQuery(name = "Atendimento.findByProblemaSolucao", query = "SELECT a FROM Atendimento a WHERE a.problemaSolucao = :problemaSolucao"),
    @NamedQuery(name = "Atendimento.findByPendencia", query = "SELECT a FROM Atendimento a WHERE a.pendencia = :pendencia"),
    @NamedQuery(name = "Atendimento.findByStatusAndUsuario", query = "SELECT a FROM Atendimento a INNER JOIN a.codusuario c "
            + "WHERE a.codstatusatendimento = :status and c.usuario like :usuario order by a.dataAgendamento,a.codprioridade")
    })
public class Atendimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODATENDIMENTO")
    private Integer codatendimento;
    @Basic(optional = false)
    @Column(name = "DATA_ABERTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAbertura;
    @Column(name = "DATA_AGENDAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAgendamento;
    @Column(name = "DATA_FECHAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFechamento;
    @Column(name = "DATA_CANCELAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCancelamento;
    @Column(name = "DATA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    @Column(name = "SOLICITANTE")
    private String solicitante;
    @Basic(optional = false)
    @Column(name = "PROBLEMA_INFORMADO")
    private String problemaInformado;
    @Column(name = "PROBLEMA_DETECTADO")
    private String problemaDetectado;
    @Column(name = "PROBLEMA_PENDENCIA")
    private String problemaPendencia;
    @Column(name = "PROBLEMA_SOLUCAO")
    private String problemaSolucao;
    @Basic(optional = false)
    @Column(name = "PENDENCIA")
    private Character pendencia;
    @JoinColumn(name = "CODTIPOATENDIMENTO", referencedColumnName = "CODTIPOATENDIMENTO")
    @ManyToOne(optional = false)
    private TipoAtendimento codtipoatendimento;
    @JoinColumn(name = "CODPRIORIDADE", referencedColumnName = "CODPRIORIDADE")
    @ManyToOne(optional = false)
    private Prioridade codprioridade;
    @JoinColumn(name = "CODCLIENTE", referencedColumnName = "CODCLIENTE")
    @ManyToOne(optional = false)
    private Cliente codcliente;
    @JoinColumn(name = "CODUSUARIO", referencedColumnName = "CODUSUARIO")
    @ManyToOne(optional = false)
    private Usuario codusuario;
    @JoinColumn(name = "CODORIGEM", referencedColumnName = "CODORIGEM")
    @ManyToOne(optional = false)
    private Origem codorigem;
    @JoinColumn(name = "CODSTATUSATENDIMENTO", referencedColumnName = "CODSTATUSATENDIMENTO")
    @ManyToOne(optional = false)
    private StatusAtendimento codstatusatendimento;

    public Atendimento() {
    }

    public Atendimento(Integer codatendimento) {
        this.codatendimento = codatendimento;
    }

    public Atendimento(Integer codatendimento, Date dataAbertura, String problemaInformado, Character pendencia) {
        this.codatendimento = codatendimento;
        this.dataAbertura = dataAbertura;
        this.problemaInformado = problemaInformado;
        this.pendencia = pendencia;
    }

    public Integer getCodatendimento() {
        return codatendimento;
    }

    public void setCodatendimento(Integer codatendimento) {
        this.codatendimento = codatendimento;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getProblemaInformado() {
        return problemaInformado;
    }

    public void setProblemaInformado(String problemaInformado) {
        this.problemaInformado = problemaInformado;
    }

    public String getProblemaDetectado() {
        return problemaDetectado;
    }

    public void setProblemaDetectado(String problemaDetectado) {
        this.problemaDetectado = problemaDetectado;
    }

    public String getProblemaPendencia() {
        return problemaPendencia;
    }

    public void setProblemaPendencia(String problemaPendencia) {
        this.problemaPendencia = problemaPendencia;
    }

    public String getProblemaSolucao() {
        return problemaSolucao;
    }

    public void setProblemaSolucao(String problemaSolucao) {
        this.problemaSolucao = problemaSolucao;
    }

    public Character getPendencia() {
        return pendencia;
    }

    public void setPendencia(Character pendencia) {
        this.pendencia = pendencia;
    }

    public TipoAtendimento getCodtipoatendimento() {
        return codtipoatendimento;
    }

    public void setCodtipoatendimento(TipoAtendimento codtipoatendimento) {
        this.codtipoatendimento = codtipoatendimento;
    }

    public Prioridade getCodprioridade() {
        return codprioridade;
    }

    public void setCodprioridade(Prioridade codprioridade) {
        this.codprioridade = codprioridade;
    }

    public Cliente getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(Cliente codcliente) {
        this.codcliente = codcliente;
    }

    public Usuario getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(Usuario codusuario) {
        this.codusuario = codusuario;
    }

    public Origem getCodorigem() {
        return codorigem;
    }

    public void setCodorigem(Origem codorigem) {
        this.codorigem = codorigem;
    }

    public StatusAtendimento getCodstatusatendimento() {
        return codstatusatendimento;
    }

    public void setCodstatusatendimento(StatusAtendimento codstatusatendimento) {
        this.codstatusatendimento = codstatusatendimento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codatendimento != null ? codatendimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atendimento)) {
            return false;
        }
        Atendimento other = (Atendimento) object;
        if ((this.codatendimento == null && other.codatendimento != null) || (this.codatendimento != null && !this.codatendimento.equals(other.codatendimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Atendimento[ codatendimento=" + codatendimento + " ]";
    }
    
}
