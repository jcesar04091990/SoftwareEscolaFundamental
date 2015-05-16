/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.DAO;

import br.com.DAO.exceptions.NonexistentEntityException;
import br.com.DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import br.com.Classes.Turma;
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
public class TurmaJpaController implements Serializable {

    public TurmaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turma turma) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(turma);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTurma(turma.getIdTurma()) != null) {
                throw new PreexistingEntityException("Turma " + turma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turma turma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            turma = em.merge(turma);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = turma.getIdTurma();
                if (findTurma(id) == null) {
                    throw new NonexistentEntityException("The turma with id " + id + " no longer exists.");
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
            Turma turma;
            try {
                turma = em.getReference(Turma.class, id);
                turma.getIdTurma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turma with id " + id + " no longer exists.", enfe);
            }
            em.remove(turma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turma> findTurmaEntities() {
        return findTurmaEntities(true, -1, -1);
    }

    public List<Turma> findTurmaEntities(int maxResults, int firstResult) {
        return findTurmaEntities(false, maxResults, firstResult);
    }

    private List<Turma> findTurmaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turma.class));
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

    public Turma findTurma(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turma.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurmaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turma> rt = cq.from(Turma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
