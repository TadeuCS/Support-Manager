package Controller;

import Model.StatusPessoa;
import Model.TipoPessoa;
import Model.TipoUsuario;
import Model.Usuario;
import Util.Classes.Manager;
import java.util.List;

public class UsuarioDAO extends Manager {

    StatusPessoaDAO statusPessoaDAO = new StatusPessoaDAO();

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

    public Usuario consultaByUsuario(String usuario) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findByUsuario").setParameter("usuario", usuario);
        em.getTransaction().commit();
        return (Usuario) query.getSingleResult();
    }

    public Usuario consultaByCPF(String cpf) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findByCpf").setParameter("cpf", cpf);
        em.getTransaction().commit();
        return (Usuario) query.getSingleResult();
    }

    public Usuario consultaByEmail(String email) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findByEmail").setParameter("email", email);
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
        query = em.createQuery("SELECT u FROM Usuario u where u.usuario = :user and u.senha= :password").setParameter("user", usuario).setParameter("password", senha);
        em.getTransaction().commit();
        return (Usuario) query.getSingleResult();
    }

    public List<Usuario> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Usuario.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public List<Usuario> listaUsuariosDesbloqueados(StatusPessoa status,TipoUsuario tipo) {
        em.getTransaction().begin();
        query = em.createQuery("SELECT u FROM Usuario u WHERE u.codstatuspessoa = :status and u.codtipousuario = :tipo").setParameter("status", status).setParameter("tipo", tipo);
        em.getTransaction().commit();
        return query.getResultList();
    }
}
