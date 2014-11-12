package Controller;

import Model.Segmento;
import Util.Classes.Manager;
import java.util.List;

public class SegmentoDAO extends Manager {

    public void salvar(Segmento app) {
        em.getTransaction().begin();
        em.merge(app);
        em.getTransaction().commit();
    }

    public List<Segmento> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Segmento.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Segmento buscaSegmento(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Segmento.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Segmento) query.getSingleResult();
    }

}
