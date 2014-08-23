/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Uf;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class EstadosDAO extends Manager {

    public void salvar(Uf estado) {
        em.getTransaction().begin();
        em.merge(estado);
        em.getTransaction().commit();
    }
    
    public List<Uf> lista(){
        em.getTransaction().begin();
        query=em.createNamedQuery("Uf.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
    public Uf consulta(String grupo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Uf.findByDescricao").setParameter("descricao", grupo);
        em.getTransaction().commit();
        return (Uf) query.getSingleResult();
    }
}