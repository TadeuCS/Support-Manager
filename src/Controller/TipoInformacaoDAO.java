package Controller;

import Model.TipoInformacao;
import Util.Classes.Manager;
import java.util.List;

public class TipoInformacaoDAO extends Manager {

    public void salvar(TipoInformacao tipoInformacao) {
        em.getTransaction().begin();
        em.merge(tipoInformacao);
        em.getTransaction().commit();
    }

    public List<TipoInformacao> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoInformacao.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public TipoInformacao buscaTipoInformacao(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("TipoInformacao.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (TipoInformacao) query.getSingleResult();
    }

}
