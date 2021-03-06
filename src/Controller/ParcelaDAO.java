package Controller;

import Model.Parcela;
import Util.Classes.Manager;
import java.util.List;

public class ParcelaDAO extends Manager {

    public void salvar(Parcela parcela) {
        em.getTransaction().begin();
        em.merge(parcela);
        em.getTransaction().commit();
    }
    
    public List<Parcela> lista(){
        em.getTransaction().begin();
        query=em.createNamedQuery("Parcela.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
    public Parcela consulta(String parcela) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Parcela.findByPercentual").setParameter("percentual", Double.parseDouble(parcela));
        em.getTransaction().commit();
        return (Parcela) query.getSingleResult();
    }
}
