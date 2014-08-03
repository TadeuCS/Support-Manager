/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Informacao;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class InformacaoDAO extends Manager {

    public void salvar(Informacao informacao) {
        em.getTransaction().begin();
        em.merge(informacao);
        em.getTransaction().commit();
    }

    public List<Informacao> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Informacao.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Informacao buscaInformacao(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Informacao.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Informacao) query.getSingleResult();
    }

}
