package Controller;

import Model.Cidade;
import Util.Classes.Manager;
import java.util.List;

public class CidadesDAO extends Manager {

    public void salvar(Cidade cidade) {
        em.getTransaction().begin();
        em.merge(cidade);
        em.getTransaction().commit();
    }
    
    public List<Cidade> lista(){
        em.getTransaction().begin();
        query=em.createNamedQuery("Cidade.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
    public Cidade consulta(String cidade) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Cidade.findByDescricao").setParameter("descricao", cidade);
        em.getTransaction().commit();
        return (Cidade) query.getSingleResult();
    }
}
