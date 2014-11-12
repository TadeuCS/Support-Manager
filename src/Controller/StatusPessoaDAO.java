package Controller;

import Model.StatusPessoa;
import Util.Classes.Manager;
import java.util.List;

public class StatusPessoaDAO extends Manager {

    public void salvar(StatusPessoa status) {
        em.getTransaction().begin();
        em.merge(status);
        em.getTransaction().commit();
    }

    public List<StatusPessoa> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("StatusPessoa.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public StatusPessoa buscaStatusPessoa(String descricao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("StatusPessoa.findByDescricao").setParameter("descricao", descricao);
        em.getTransaction().commit();
        return (StatusPessoa) query.getSingleResult();
    }

}
