/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Empresa;
import Util.Classes.Manager;
import java.util.List;

/**
 *
 * @author Tadeu
 */
public class EmpresaDAO extends Manager {

    public void salvar(Empresa empresa) {
        em.getTransaction().begin();
        em.merge(empresa);
        em.getTransaction().commit();
    }

    public Empresa findByNomeFantasiaOrCNPJ(String nomeFantasia, String cnpj) {
        em.getTransaction().begin();
        query = em.createQuery("SELECT e FROM Empresa e WHERE e.cnpjCpf = :cnpj OR e.nomeFantasia = :nome").setParameter("cnpj", cnpj).setParameter("nome", nomeFantasia);
        em.getTransaction().commit();
        return (Empresa) query.getSingleResult();
    }

    public List<Empresa> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Empresa.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }
    public Empresa findByCodigo(int codigo){
        em.getTransaction().begin();
        query = em.createNamedQuery("Empresa.findByCodempresa").setParameter("codempresa", codigo);
        em.getTransaction().commit();
        return (Empresa) query.getSingleResult();
    }

}
