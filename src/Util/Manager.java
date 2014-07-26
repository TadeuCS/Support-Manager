/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Tadeu
 */
public class Manager {
    protected Query query;
    protected EntityManagerFactory emf= Persistence.createEntityManagerFactory("Support_ManagerPU");
    protected EntityManager em= emf.createEntityManager();
}
