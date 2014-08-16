/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Grupo;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
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
