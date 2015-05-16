/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.DAO;

import br.com.DAO.exceptions.NonexistentEntityException;
import br.com.DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.Classes.Funcionario;
import br.com.Classes.Professor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class ProfessorJpaController implements Serializable {

    public ProfessorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Professor professor) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario codFunc = professor.getCodFunc();
            if (codFunc != null) {
                codFunc = em.getReference(codFunc.getClass(), codFunc.getCodFunc());
                professor.setCodFunc(codFunc);
            }
            em.persist(professor);
            if (codFunc != null) {
                codFunc.getProfessorCollection().add(professor);
                codFunc = em.merge(codFunc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProfessor(professor.getIdProfessor()) != null) {
                throw new PreexistingEntityException("Professor " + professor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Professor professor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professor persistentProfessor = em.find(Professor.class, professor.getIdProfessor());
            Funcionario codFuncOld = persistentProfessor.getCodFunc();
            Funcionario codFuncNew = professor.getCodFunc();
            if (codFuncNew != null) {
                codFuncNew = em.getReference(codFuncNew.getClass(), codFuncNew.getCodFunc());
                professor.setCodFunc(codFuncNew);
            }
            professor = em.merge(professor);
            if (codFuncOld != null && !codFuncOld.equals(codFuncNew)) {
                codFuncOld.getProfessorCollection().remove(professor);
                codFuncOld = em.merge(codFuncOld);
            }
            if (codFuncNew != null && !codFuncNew.equals(codFuncOld)) {
                codFuncNew.getProfessorCollection().add(professor);
                codFuncNew = em.merge(codFuncNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = professor.getIdProfessor();
                if (findProfessor(id) == null) {
                    throw new NonexistentEntityException("The professor with id " + id + " no longer exists.");
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
            Professor professor;
            try {
                professor = em.getReference(Professor.class, id);
                professor.getIdProfessor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The professor with id " + id + " no longer exists.", enfe);
            }
            Funcionario codFunc = professor.getCodFunc();
            if (codFunc != null) {
                codFunc.getProfessorCollection().remove(professor);
                codFunc = em.merge(codFunc);
            }
            em.remove(professor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Professor> findProfessorEntities() {
        return findProfessorEntities(true, -1, -1);
    }

    public List<Professor> findProfessorEntities(int maxResults, int firstResult) {
        return findProfessorEntities(false, maxResults, firstResult);
    }

    private List<Professor> findProfessorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Professor.class));
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

    public Professor findProfessor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Professor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfessorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Professor> rt = cq.from(Professor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
