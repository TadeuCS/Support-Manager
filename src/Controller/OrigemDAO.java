package Controller;

import Model.Origem;
import Util.Classes.Manager;
import java.util.List;

public class OrigemDAO extends Manager {

    public void salvar(Origem origem) {
        em.getTransaction().begin();
        em.merge(origem);
        em.getTransaction().commit();
    }

    public List<Origem> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Origem.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Origem buscaOrigem(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Origem.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Origem) query.getSingleResult();
    }

}
