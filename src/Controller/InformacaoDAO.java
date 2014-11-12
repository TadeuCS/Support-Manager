package Controller;

import Model.Informacao;
import Util.Classes.Manager;
import java.util.List;

public class InformacaoDAO extends Manager {

    public void salvar(Informacao informacao) {
        em.getTransaction().begin();
        em.merge(informacao);
        em.getTransaction().commit();
    }

    public List<Informacao> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Informacao.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Informacao buscaInformacao(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Informacao.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (Informacao) query.getSingleResult();
    }

}
