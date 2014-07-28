/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Prioridade;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class PrioridadeDAO extends Manager {

    public void salvar(Prioridade prioridade) {
        em.getTransaction().begin();
        em.merge(prioridade);
        em.getTransaction().commit();
    }

    public List<Prioridade> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Prioridade.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Prioridade buscaPrioridade(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Prioridade.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Prioridade) query.getSingleResult();
    }

}
