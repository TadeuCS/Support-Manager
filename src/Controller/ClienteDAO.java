/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cliente;
import Model.Endereco;
import Model.LinkCliente;
import Model.Telefone;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class ClienteDAO extends Manager {

    public void salvar(Cliente cliente) {
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
    }

    public void remover(Cliente cliente) {
        em.getTransaction().begin();
        em.remove(cliente);
        em.getTransaction().commit();
    }

    public List<Cliente> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Cliente.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Cliente buscaClienteByCodigo(int codigo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Cliente.findByCodcliente").setParameter("codcliente", codigo);
        em.getTransaction().commit();
        return (Cliente) query.getSingleResult();
    }
    
    public Cliente buscaClienteByRazao(String razao) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Cliente.findByRazaoSocial").setParameter("razaoSocial", razao);
        em.getTransaction().commit();
        return (Cliente) query.getSingleResult();
    }
    
    public Cliente buscaClienteByCNPJ(String cnpj) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Cliente.findByCnpjCpf").setParameter("cnpjCpf", cnpj);
        em.getTransaction().commit();
        return (Cliente) query.getSingleResult();
    }
    
    public Cliente buscaClienteByEmail(String email) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Cliente.findByEmail").setParameter("email", email);
        em.getTransaction().commit();
        return (Cliente) query.getSingleResult();
    }

    public List<Telefone> listaTelefonesByCliente(Cliente cliente) {
        em.getTransaction().begin();
        query = em.createQuery("SELECT c FROM Telefone c where c.codcliente = :cliente").setParameter("cliente", cliente);
        em.getTransaction().commit();
        return query.getResultList();
    }

    public List<LinkCliente> listalinksByCliente(Cliente cliente) {
        em.getTransaction().begin();
        query = em.createNamedQuery("LinkCliente.findByClientecodcliente").setParameter("clientecodcliente", cliente);
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Endereco findEnderecoByCEP(String cep) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Endereco.findByCep").setParameter("cep", cep);
        em.getTransaction().commit();
        return (Endereco) query.getSingleResult();
    }
}
