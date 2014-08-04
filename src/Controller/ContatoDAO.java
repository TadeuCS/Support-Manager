/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Contato;
import Model.Contato;
import Util.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class ContatoDAO extends Manager {

    public void salvar(Contato cntato) {
        em.getTransaction().begin();
        em.merge(cntato);
        em.getTransaction().commit();
    }
    
    public List<Contato> lista(){
        em.getTransaction().begin();
        query=em.createNamedQuery("Contato.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
}
