/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Link;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class LinksDAO extends Manager {

    public void salvar(Link link) {
        em.getTransaction().begin();
        em.merge(link);
        em.getTransaction().commit();
    }

    public List<Link> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Link.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Link buscaLink(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Link.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Link) query.getSingleResult();
    }

}
