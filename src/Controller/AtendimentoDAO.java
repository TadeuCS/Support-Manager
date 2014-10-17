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

/**
 *
 * @author Tadeu
 */
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

    public Atendimento getByCodigo(int codigo){
        em.getTransaction().begin();
        query = em.createNamedQuery("Atendimento.findByCodatendimento").setParameter("codatendimento", codigo);
        em.getTransaction().commit();
        return (Atendimento) query.getSingleResult();
    }
    public List<Atendimento> listaByStatusAndUsuario(StatusAtendimento status,Usuario usuario) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Atendimento.findByStatusAndUsuario").setParameter("status", status).setParameter("codUsuario", usuario);
        em.getTransaction().commit();
        return query.getResultList();
    }
    public List<Atendimento> listaByStatus(StatusAtendimento status) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Atendimento.findByStatus").setParameter("status", status);
        em.getTransaction().commit();
        return query.getResultList();
    }
}
