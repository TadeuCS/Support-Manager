package Controller;

import Model.Empresa;
import Util.Classes.Manager;
import java.util.List;

public class EmpresaDAO extends Manager {

    public void salvar(Empresa empresa) {
        em.getTransaction().begin();
        em.merge(empresa);
        em.getTransaction().commit();
    }

    public Empresa findByNomeFantasia(String nomeFantasia) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Empresa.findByNomeFantasia").setParameter("nomeFantasia", nomeFantasia);
        em.getTransaction().commit();
        return (Empresa) query.getSingleResult();
    }

    public Empresa findByCNPJ(String cnpj) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Empresa.findByCnpjCpf").setParameter("cnpjCpf", cnpj);
        em.getTransaction().commit();
        return (Empresa) query.getSingleResult();
    }

    public List<Empresa> lista() {
        em.getTransaction().begin();
        query = em.createNamedQuery("Empresa.findAll");
        em.getTransaction().commit();
        return query.getResultList();
    }

    public Empresa findByCodigo(int codigo) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Empresa.findByCodempresa").setParameter("codempresa", codigo);
        em.getTransaction().commit();
        return (Empresa) query.getSingleResult();
    }

}
