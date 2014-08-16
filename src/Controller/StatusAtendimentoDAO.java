/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.StatusAtendimento;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class StatusAtendimentoDAO extends Manager {

    public void salvar(StatusAtendimento status) {
        em.getTransaction().begin();
        em.merge(status);
        em.getTransaction().commit();
    }

    public List<StatusAtendimento> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("StatusAtendimento.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public StatusAtendimento buscaStatusAtendimento(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("StatusAtendimento.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (StatusAtendimento) query.getSingleResult();
    }

}
