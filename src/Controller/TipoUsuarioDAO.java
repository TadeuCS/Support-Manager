package Controller;

import Model.TipoUsuario;
import Util.Classes.Manager;
import java.util.List;

public class TipoUsuarioDAO extends Manager {

    private TipoUsuario tipoUsuario;

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
