/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Aplicativo;
import Model.Cliente;
import Model.Link;
import Model.LinkCliente;
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
    public void removerLinkCliente(LinkCliente link){
        em.getTransaction().begin();
        em.remove(link);
        em.getTransaction().commit();
    }
    public List<Link> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Link.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
    public LinkCliente findLinkClienteByCodigo(int codigo){
        em.getTransaction().begin();
        query = em.createQuery("SELECT lc  FROM LinkCliente lc WHERE lc.codLinkCliente=:codigo").setParameter("codigo", codigo);
        em.getTransaction().commit();
        return (LinkCliente) query.getSingleResult();
    }
    public Link buscaLink(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Link.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Link) query.getSingleResult();
    }

    public List<Link> listaLinksByCliente(Cliente cliente) {
        em.getTransaction().begin();
        query = em.createNamedQuery("LinkCliente.findByClientecodcliente").setParameter("clientecodcliente", cliente);
        em.getTransaction().commit();
        return query.getResultList();
    }
    public List<Link> listaLinksByAplicativo(Aplicativo aplicativo) {
        em.getTransaction().begin();
        query = em.createQuery("SELECT a FROM Link a WHERE a.codaplicativo= :app order by a.descricao").setParameter("app", aplicativo);
        em.getTransaction().commit();
        return query.getResultList();
    }
}
