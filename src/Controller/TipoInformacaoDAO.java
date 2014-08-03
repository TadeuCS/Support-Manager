/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.TipoInformacao;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class TipoInformacaoDAO extends Manager {

    public void salvar(TipoInformacao tipoInformacao) {
        em.getTransaction().begin();
        em.merge(tipoInformacao);
        em.getTransaction().commit();
    }

    public List<TipoInformacao> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoInformacao.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public TipoInformacao buscaTipoInformacao(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoInformacao.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (TipoInformacao) query.getSingleResult();
    }

}
