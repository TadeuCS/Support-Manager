/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Segmento;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class SegmentoDAO extends Manager {

    public void salvar(Segmento app) {
        em.getTransaction().begin();
        em.merge(app);
        em.getTransaction().commit();
    }

    public List<Segmento> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Segmento.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Segmento buscaSegmento(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Segmento.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Segmento) query.getSingleResult();
    }

}
