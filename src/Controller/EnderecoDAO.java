package Controller;

import Model.Empresa;
import Model.Endereco;
import Util.Classes.Manager;

public class EnderecoDAO extends Manager {

    public void salvar(Endereco endereco) {
        em.getTransaction().begin();
        em.merge(endereco);
        em.getTransaction().commit();
    }
    public Endereco findEnderecoByCEP(String cep){
        em.getTransaction().begin();
        query=em.createNamedQuery("Endereco.findByCep").setParameter("cep", cep);
        em.getTransaction().commit();
        return (Endereco) query.getSingleResult();
    }
}
