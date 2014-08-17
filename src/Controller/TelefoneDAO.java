/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Telefone;
import Model.Usuario;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class TelefoneDAO extends Manager {

    public void salvar(Telefone telefone) {
        em.getTransaction().begin();
        em.merge(telefone);
        em.getTransaction().commit();
    }

    public List<Telefone> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Telefone.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public List<Telefone> listaByUsuario(Usuario usuario) {
        em.getTransaction().begin();
        query = em.createQuery("SELECT t FROM Telefone t where t.codusuario= :usuario").setParameter("usuario", usuario);
        em.getTransaction().commit();
        return query.getResultList();
    }
}
