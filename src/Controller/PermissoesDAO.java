/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Permissoes;
import Model.TipoUsuario;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class PermissoesDAO extends Manager {

    public void salvar(Permissoes permissoes) {
        em.getTransaction().begin();
        em.merge(permissoes);
        em.getTransaction().commit();
    }

    public Permissoes findByTipoUsuario(TipoUsuario tipo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Permissoes.findByTipoUsuario").setParameter("tipoUsuario", tipo);
        em.getTransaction().commit();
        return (Permissoes) query.getSingleResult();
    }

}
