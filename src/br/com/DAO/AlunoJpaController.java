/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.DAO;

import br.com.DAO.exceptions.NonexistentEntityException;
import br.com.DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import br.com.Classes.Aluno;
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
public class AlunoJpaController implements Serializable {

    public AlunoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aluno aluno) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario codFunc = aluno.getCodFunc();
            if (codFunc != null) {
                codFunc = em.getReference(codFunc.getClass(), codFunc.getCodFunc());
                aluno.setCodFunc(codFunc);
            }
            em.persist(aluno);
            if (codFunc != null) {
                codFunc.getAlunoCollection().add(aluno);
                codFunc = em.merge(codFunc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAluno(aluno.getIdAluno()) != null) {
                throw new PreexistingEntityException("Aluno " + aluno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Aluno aluno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aluno persistentAluno = em.find(Aluno.class, aluno.getIdAluno());
            Funcionario codFuncOld = persistentAluno.getCodFunc();
            Funcionario codFuncNew = aluno.getCodFunc();
            if (codFuncNew != null) {
                codFuncNew = em.getReference(codFuncNew.getClass(), codFuncNew.getCodFunc());
                aluno.setCodFunc(codFuncNew);
            }
            aluno = em.merge(aluno);
            if (codFuncOld != null && !codFuncOld.equals(codFuncNew)) {
                codFuncOld.getAlunoCollection().remove(aluno);
                codFuncOld = em.merge(codFuncOld);
            }
            if (codFuncNew != null && !codFuncNew.equals(codFuncOld)) {
                codFuncNew.getAlunoCollection().add(aluno);
                codFuncNew = em.merge(codFuncNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = aluno.getIdAluno();
                if (findAluno(id) == null) {
                    throw new NonexistentEntityException("The aluno with id " + id + " no longer exists.");
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
            Aluno aluno;
            try {
                aluno = em.getReference(Aluno.class, id);
                aluno.getIdAluno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aluno with id " + id + " no longer exists.", enfe);
            }
            Funcionario codFunc = aluno.getCodFunc();
            if (codFunc != null) {
                codFunc.getAlunoCollection().remove(aluno);
                codFunc = em.merge(codFunc);
            }
            em.remove(aluno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Aluno> findAlunoEntities() {
        return findAlunoEntities(true, -1, -1);
    }

    public List<Aluno> findAlunoEntities(int maxResults, int firstResult) {
        return findAlunoEntities(false, maxResults, firstResult);
    }

    private List<Aluno> findAlunoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aluno.class));
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

    public Aluno findAluno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aluno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlunoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aluno> rt = cq.from(Aluno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
