/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.TipoAtendimento;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class TipoAtendimentoDAO extends Manager {

    public void salvar(TipoAtendimento app) {
        em.getTransaction().begin();
        em.merge(app);
        em.getTransaction().commit();
    }

    public List<TipoAtendimento> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoAtendimento.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public TipoAtendimento buscaTipoAtendimento(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoAtendimento.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (TipoAtendimento) query.getSingleResult();
    }

}
