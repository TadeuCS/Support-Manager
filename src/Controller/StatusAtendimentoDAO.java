package Controller;

import Model.StatusAtendimento;
import Util.Classes.Manager;
import java.util.List;

public class StatusAtendimentoDAO extends Manager {

    public void salvar(StatusAtendimento status) {
        em.getTransaction().begin();
        em.merge(status);
        em.getTransaction().commit();
    }

    public List<StatusAtendimento> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("StatusAtendimento.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public StatusAtendimento buscaStatusAtendimento(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("StatusAtendimento.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (StatusAtendimento) query.getSingleResult();
    }

}
