package Controller;

import Model.Aplicativo;
import Util.Classes.Manager;
import java.util.List;

public class AplicativoDAO extends Manager {

    public void salvar(Aplicativo app) {
        em.getTransaction().begin();
        em.merge(app);
        em.getTransaction().commit();
    }

    public List<Aplicativo> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Aplicativo.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Aplicativo buscaAplicativo(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Aplicativo.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Aplicativo) query.getSingleResult();
    }

}
