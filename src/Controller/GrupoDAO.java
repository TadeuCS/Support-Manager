package Controller;

import Model.Grupo;
import Util.Classes.Manager;
import java.util.List;

public class GrupoDAO extends Manager {

    public void salvar(Grupo grupo) {
        em.getTransaction().begin();
        em.merge(grupo);
        em.getTransaction().commit();
    }
    
    public List<Grupo> lista(){
        em.getTransaction().begin();
        query=em.createNamedQuery("Grupo.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
    public Grupo consulta(String grupo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Grupo.findByDescricao").setParameter("descricao", grupo);
        em.getTransaction().commit();
        return (Grupo) query.getSingleResult();
    }
}
