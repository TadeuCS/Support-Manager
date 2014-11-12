package Controller;

import Model.Permissoes;
import Model.TipoUsuario;
import Util.Classes.Manager;
import java.util.List;

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
