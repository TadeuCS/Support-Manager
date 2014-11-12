package Controller;

import Model.Salarios;
import Util.Classes.Manager;
import java.util.List;

public class SalarioDAO extends Manager {

    public void salva(Salarios salario){
        em.getTransaction().begin();
        em.merge(salario);
        em.getTransaction().commit();
    }
    public List<Salarios> lista(){
        em.getTransaction().begin();
        query=em.createNamedQuery("Salarios.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
//    public Double buscaSalario(int ano){
//        em.getTransaction().begin();
//        query=em.createNamedQuery("Salarios.findByAno").setParameter("ano", ano);
//        em.getTransaction().commit();
//        return (Double) query.getSingleResult();
//    }
}
