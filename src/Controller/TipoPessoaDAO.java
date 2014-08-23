/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.TipoPessoa;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class TipoPessoaDAO extends Manager {

    private TipoPessoa tipoUsuario;

    public void salvar(TipoPessoa tipoPessoa) {
        em.getTransaction().begin();
        em.merge(tipoPessoa);
        em.getTransaction().commit();
    }

    public List<TipoPessoa> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoPessoa.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public TipoPessoa buscaTipoPessoa(String tipo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoPessoa.findByDescricao").setParameter("descricao", tipo);
        em.getTransaction().commit();
        return (TipoPessoa) query.getSingleResult();
    }

}
