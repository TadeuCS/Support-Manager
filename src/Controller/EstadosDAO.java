package Controller;

import Model.Cidade;
import Model.Uf;
import Util.Classes.Manager;
import java.util.List;

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
    public Uf cunsultaByEstado(String estado) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Uf.findByDescricao").setParameter("descricao", estado);
        em.getTransaction().commit();
        return (Uf) query.getSingleResult();
    }
    public Cidade cunsultaByIBGE(int ibge) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Cidade.findByCodibgemunicipio").setParameter("codibgemunicipio", ibge);
        em.getTransaction().commit();
        return  (Cidade) query.getSingleResult();
    }
    public Uf cunsultaBySigla(String sigla) {
        em.getTransaction().begin();
        query = em.createNamedQuery("Uf.findBySigla").setParameter("sigla", sigla);
        em.getTransaction().commit();
        return (Uf) query.getSingleResult();
    }
}
