/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cliente;
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

    public void apagar(Telefone telefone) {
        em.getTransaction().begin();
        em.remove(telefone);
        em.getTransaction().commit();
    }

    public Telefone busca(String telefone) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Telefone.findByTelefone").setParameter("telefone", telefone);
        em.getTransaction().commit();
        return (Telefone) query.getSingleResult();
    }

    public List<Telefone> listaByUsuario(Usuario usuario) {
        em.getTransaction().begin();
        query = em.createQuery("SELECT t FROM Telefone t where t.codusuario= :usuario order by t.descricao").setParameter("usuario", usuario);
        em.getTransaction().commit();
        return query.getResultList();
    }

    public List<Telefone> listaTelefoneByCliente(Cliente cliente) {
        em.getTransaction().begin();
        query = em.createQuery("SELECT t FROM Telefone t where t.codcliente =:codcliente order by t.descricao").setParameter("codcliente", cliente);
        em.getTransaction().commit();
        return query.getResultList();
    }
}
