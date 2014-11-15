package Controller;

import Model.Salario;
import Util.Classes.Manager;
import java.util.List;

public class SalarioDAO extends Manager {

    public void salva(Salario salario){
        em.getTransaction().begin();
        em.merge(salario);
        em.getTransaction().commit();
    }
    public List<Salario> lista(){
        em.getTransaction().begin();
        query=em.createNamedQuery("Salario.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
    public Salario findSalarioByCodigo(int codigo){
        em.getTransaction().begin();
        query=em.createNamedQuery("Salario.findByCodsalario").setParameter("codsalario", codigo);
        em.getTransaction().commit();
        return (Salario) query.getSingleResult();
    }
}
