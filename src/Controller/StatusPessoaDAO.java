/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.StatusPessoa;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class StatusPessoaDAO extends Manager {

    public void salvar(StatusPessoa status) {
        em.getTransaction().begin();
        em.merge(status);
        em.getTransaction().commit();
    }

    public List<StatusPessoa> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("StatusPessoa.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public StatusPessoa buscaStatusPessoa(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("StatusPessoa.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (StatusPessoa) query.getSingleResult();
    }

}
