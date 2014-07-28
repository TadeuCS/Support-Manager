/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Aplicativo;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class AplicativoDAO extends Manager {

    public void salvar(Aplicativo app) {
        em.getTransaction().begin();
        em.merge(app);
        em.getTransaction().commit();
    }

    public List<Aplicativo> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Aplicativo.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Aplicativo buscaAplicativo(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Aplicativo.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Aplicativo) query.getSingleResult();
    }

}
