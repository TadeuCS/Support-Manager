/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Atendimento;
import Model.StatusAtendimento;
import Model.Usuario;
import Util.Classes.Manager;
import java.util.List;

public class AtendimentoDAO extends Manager {

    public void salvar(Atendimento att) {
        em.getTransaction().begin();
        em.merge(att);
        em.getTransaction().commit();
    }

    public void remove(Atendimento att) {
        em.getTransaction().begin();
        em.remove(att);
        em.getTransaction().commit();
    }

    public List getCountAtendimentos(String usuarioLogado) {
        em.getTransaction().begin();
        query = em.createNativeQuery("select s.`DESCRICAO`,count(a.`CODATENDIMENTO`) QTDE from atendimento a\n"
                + "inner join status_atendimento s on a.`CODSTATUSATENDIMENTO`=s.`CODSTATUSATENDIMENTO`\n"
                + "inner join usuario u on a.`CODUSUARIO`=u.`CODUSUARIO`\n"
                + "where u.`USUARIO` like '" + usuarioLogado + "'\n"
                + "group by a.`CODSTATUSATENDIMENTO`;");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Atendimento getByCodigo(int codigo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Atendimento.findByCodatendimento").setParameter("codatendimento", codigo);
        em.getTransaction().commit();
        return (Atendimento) query.getSingleResult();
    }

    public List<Atendimento> listaByStatusAndUsuario(StatusAtendimento status, String usuario) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Atendimento.findByStatusAndUsuario").setParameter("status", status).setParameter("usuario", usuario);
        em.getTransaction().commit();
        return query.getResultList();
    }
}