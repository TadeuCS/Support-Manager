/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Usuario;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class UsuarioDAO extends Manager {

    public void salvar(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public Usuario findByCodigo(int codigo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findByCodusuario");
        em.getTransaction().commit();
        return (Usuario) query.getSingleResult();
    }

    public Usuario findByUsuarioAndSenha(String usuario, String senha) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findByLogin")
                .setParameter("user", usuario).setParameter("password", senha).setParameter("bloc", "N");
        em.getTransaction().commit();
        return (Usuario) query.getSingleResult();
    }

    public List<Usuario> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
}
