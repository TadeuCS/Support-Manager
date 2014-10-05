/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Empresa;
import Model.Endereco;
import Util.Classes.Manager;

/**
 *
 * @author Tadeu
 */
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
