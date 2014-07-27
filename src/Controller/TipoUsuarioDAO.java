/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.TipoUsuario;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class TipoUsuarioDAO extends Manager {

    public void salvar(TipoUsuario tipoUsuario) {
        em.getTransaction().begin();
        em.merge(tipoUsuario);
        em.getTransaction().commit();
    }

    public List<TipoUsuario> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoUsuario.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public TipoUsuario buscaTipoUsuario(String tipo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoUsuario.findByDescricao").setParameter("descricao", tipo);
        em.getTransaction().commit();
        return (TipoUsuario) query.getSingleResult();
    }

}
