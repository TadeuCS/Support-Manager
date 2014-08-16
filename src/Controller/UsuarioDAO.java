/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Usuario;
import Util.Classes.Criptografia;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class UsuarioDAO extends Manager {

    private Usuario usuario;

    public UsuarioDAO() {
        usuario = new Usuario();
        usuario.setUsuario("admin".toUpperCase());
        usuario.setSenha(Criptografia.criptografar("adm123"));
        usuario.setCpf("1");
        usuario.setEmail("teste@hotmail.com");
        usuario.setNome("administrador");
        usuario.setSexo('M');
//        usuario.setCodtipousuario(null);
//        usuario.setCodstatuspessoa();
        try {
            usuario.setCodusuario(consulta(usuario.getUsuario()).getCodusuario());
        } catch (Exception e) {
        } finally {
            salvar(usuario);
        }
    }

    public void salvar(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void excluir(Usuario usuario) {
        em.getTransaction().begin();
        em.remove(usuario);
        em.getTransaction().commit();
    }

    public Usuario consulta(String usuario) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findByUsuario").setParameter("usuario", usuario);
        em.getTransaction().commit();
        return (Usuario) query.getSingleResult();
    }

    public Usuario findByCodigo(int codigo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findByCodusuario").setParameter("codusuario", codigo);
        em.getTransaction().commit();
        return (Usuario) query.getSingleResult();
    }

    public Usuario findByUsuarioAndSenha(String usuario, String senha) {
        em.getTransaction().begin();
        query = em.createQuery("SELECT u FROM Usuario u where u.usuario = :user and u.senha= :password and u.bloqueado = :bloc")
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
