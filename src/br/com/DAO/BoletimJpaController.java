/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.DAO;

import br.com.DAO.exceptions.NonexistentEntityException;
import br.com.DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import br.com.Classes.Boletim;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author usuario
 */
public class BoletimJpaController implements Serializable {

    public BoletimJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Boletim boletim) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(boletim);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBoletim(boletim.getIdBoletim()) != null) {
                throw new PreexistingEntityException("Boletim " + boletim + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boletim boletim) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            boletim = em.merge(boletim);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = boletim.getIdBoletim();
                if (findBoletim(id) == null) {
                    throw new NonexistentEntityException("The boletim with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boletim boletim;
            try {
                boletim = em.getReference(Boletim.class, id);
                boletim.getIdBoletim();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boletim with id " + id + " no longer exists.", enfe);
            }
            em.remove(boletim);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boletim> findBoletimEntities() {
        return findBoletimEntities(true, -1, -1);
    }

    public List<Boletim> findBoletimEntities(int maxResults, int firstResult) {
        return findBoletimEntities(false, maxResults, firstResult);
    }

    private List<Boletim> findBoletimEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Boletim.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Boletim findBoletim(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boletim.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletimCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Boletim> rt = cq.from(Boletim.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
