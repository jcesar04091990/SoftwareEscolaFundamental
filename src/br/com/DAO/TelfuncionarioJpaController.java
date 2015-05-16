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
import br.com.Classes.Telfuncionario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class TelfuncionarioJpaController implements Serializable {

    public TelfuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telfuncionario telfuncionario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario codFunc = telfuncionario.getCodFunc();
            if (codFunc != null) {
                codFunc = em.getReference(codFunc.getClass(), codFunc.getCodFunc());
                telfuncionario.setCodFunc(codFunc);
            }
            em.persist(telfuncionario);
            if (codFunc != null) {
                codFunc.getTelfuncionarioCollection().add(telfuncionario);
                codFunc = em.merge(codFunc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTelfuncionario(telfuncionario.getIdTelFunc()) != null) {
                throw new PreexistingEntityException("Telfuncionario " + telfuncionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telfuncionario telfuncionario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telfuncionario persistentTelfuncionario = em.find(Telfuncionario.class, telfuncionario.getIdTelFunc());
            Funcionario codFuncOld = persistentTelfuncionario.getCodFunc();
            Funcionario codFuncNew = telfuncionario.getCodFunc();
            if (codFuncNew != null) {
                codFuncNew = em.getReference(codFuncNew.getClass(), codFuncNew.getCodFunc());
                telfuncionario.setCodFunc(codFuncNew);
            }
            telfuncionario = em.merge(telfuncionario);
            if (codFuncOld != null && !codFuncOld.equals(codFuncNew)) {
                codFuncOld.getTelfuncionarioCollection().remove(telfuncionario);
                codFuncOld = em.merge(codFuncOld);
            }
            if (codFuncNew != null && !codFuncNew.equals(codFuncOld)) {
                codFuncNew.getTelfuncionarioCollection().add(telfuncionario);
                codFuncNew = em.merge(codFuncNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telfuncionario.getIdTelFunc();
                if (findTelfuncionario(id) == null) {
                    throw new NonexistentEntityException("The telfuncionario with id " + id + " no longer exists.");
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
            Telfuncionario telfuncionario;
            try {
                telfuncionario = em.getReference(Telfuncionario.class, id);
                telfuncionario.getIdTelFunc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telfuncionario with id " + id + " no longer exists.", enfe);
            }
            Funcionario codFunc = telfuncionario.getCodFunc();
            if (codFunc != null) {
                codFunc.getTelfuncionarioCollection().remove(telfuncionario);
                codFunc = em.merge(codFunc);
            }
            em.remove(telfuncionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telfuncionario> findTelfuncionarioEntities() {
        return findTelfuncionarioEntities(true, -1, -1);
    }

    public List<Telfuncionario> findTelfuncionarioEntities(int maxResults, int firstResult) {
        return findTelfuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Telfuncionario> findTelfuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telfuncionario.class));
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

    public Telfuncionario findTelfuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telfuncionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelfuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telfuncionario> rt = cq.from(Telfuncionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
