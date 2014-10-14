/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Atendimento;
import Util.Classes.Manager;

/**
 *
 * @author Tadeu
 */
public class AtendimentoDAO extends Manager{
    
    public void salvar(Atendimento att){
        em.getTransaction().begin();
        em.merge(att);
        em.getTransaction().commit();
    }
}
