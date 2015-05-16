/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.DAO;

import br.com.DAO.exceptions.NonexistentEntityException;
import br.com.DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import br.com.Classes.Colaborador;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.Classes.Funcionario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class ColaboradorJpaController implements Serializable {

    public ColaboradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Colaborador colaborador) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario codFunc = colaborador.getCodFunc();
            if (codFunc != null) {
                codFunc = em.getReference(codFunc.getClass(), codFunc.getCodFunc());
                colaborador.setCodFunc(codFunc);
            }
            em.persist(colaborador);
            if (codFunc != null) {
                codFunc.getColaboradorCollection().add(colaborador);
                codFunc = em.merge(codFunc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findColaborador(colaborador.getIdColaborador()) != null) {
                throw new PreexistingEntityException("Colaborador " + colaborador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Colaborador colaborador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Colaborador persistentColaborador = em.find(Colaborador.class, colaborador.getIdColaborador());
            Funcionario codFuncOld = persistentColaborador.getCodFunc();
            Funcionario codFuncNew = colaborador.getCodFunc();
            if (codFuncNew != null) {
                codFuncNew = em.getReference(codFuncNew.getClass(), codFuncNew.getCodFunc());
                colaborador.setCodFunc(codFuncNew);
            }
            colaborador = em.merge(colaborador);
            if (codFuncOld != null && !codFuncOld.equals(codFuncNew)) {
                codFuncOld.getColaboradorCollection().remove(colaborador);
                codFuncOld = em.merge(codFuncOld);
            }
            if (codFuncNew != null && !codFuncNew.equals(codFuncOld)) {
                codFuncNew.getColaboradorCollection().add(colaborador);
                codFuncNew = em.merge(codFuncNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = colaborador.getIdColaborador();
                if (findColaborador(id) == null) {
                    throw new NonexistentEntityException("The colaborador with id " + id + " no longer exists.");
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
            Colaborador colaborador;
            try {
                colaborador = em.getReference(Colaborador.class, id);
                colaborador.getIdColaborador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colaborador with id " + id + " no longer exists.", enfe);
            }
            Funcionario codFunc = colaborador.getCodFunc();
            if (codFunc != null) {
                codFunc.getColaboradorCollection().remove(colaborador);
                codFunc = em.merge(codFunc);
            }
            em.remove(colaborador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Colaborador> findColaboradorEntities() {
        return findColaboradorEntities(true, -1, -1);
    }

    public List<Colaborador> findColaboradorEntities(int maxResults, int firstResult) {
        return findColaboradorEntities(false, maxResults, firstResult);
    }

    private List<Colaborador> findColaboradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Colaborador.class));
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

    public Colaborador findColaborador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Colaborador.class, id);
        } finally {
            em.close();
        }
    }

    public int getColaboradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Colaborador> rt = cq.from(Colaborador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
