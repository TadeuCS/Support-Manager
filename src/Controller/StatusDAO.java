/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Status;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class StatusDAO extends Manager {

    public void salvar(Status status) {
        em.getTransaction().begin();
        em.merge(status);
        em.getTransaction().commit();
    }

    public List<Status> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Status.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Status buscaStatus(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Status.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Status) query.getSingleResult();
    }

}
