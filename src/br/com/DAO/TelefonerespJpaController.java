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
import br.com.Classes.Responsavel;
import br.com.Classes.Telefoneresp;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class TelefonerespJpaController implements Serializable {

    public TelefonerespJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefoneresp telefoneresp) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Responsavel idResponsavel = telefoneresp.getIdResponsavel();
            if (idResponsavel != null) {
                idResponsavel = em.getReference(idResponsavel.getClass(), idResponsavel.getIdResponsavel());
                telefoneresp.setIdResponsavel(idResponsavel);
            }
            em.persist(telefoneresp);
            if (idResponsavel != null) {
                idResponsavel.getTelefonerespCollection().add(telefoneresp);
                idResponsavel = em.merge(idResponsavel);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTelefoneresp(telefoneresp.getIdTelResp()) != null) {
                throw new PreexistingEntityException("Telefoneresp " + telefoneresp + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefoneresp telefoneresp) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefoneresp persistentTelefoneresp = em.find(Telefoneresp.class, telefoneresp.getIdTelResp());
            Responsavel idResponsavelOld = persistentTelefoneresp.getIdResponsavel();
            Responsavel idResponsavelNew = telefoneresp.getIdResponsavel();
            if (idResponsavelNew != null) {
                idResponsavelNew = em.getReference(idResponsavelNew.getClass(), idResponsavelNew.getIdResponsavel());
                telefoneresp.setIdResponsavel(idResponsavelNew);
            }
            telefoneresp = em.merge(telefoneresp);
            if (idResponsavelOld != null && !idResponsavelOld.equals(idResponsavelNew)) {
                idResponsavelOld.getTelefonerespCollection().remove(telefoneresp);
                idResponsavelOld = em.merge(idResponsavelOld);
            }
            if (idResponsavelNew != null && !idResponsavelNew.equals(idResponsavelOld)) {
                idResponsavelNew.getTelefonerespCollection().add(telefoneresp);
                idResponsavelNew = em.merge(idResponsavelNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefoneresp.getIdTelResp();
                if (findTelefoneresp(id) == null) {
                    throw new NonexistentEntityException("The telefoneresp with id " + id + " no longer exists.");
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
            Telefoneresp telefoneresp;
            try {
                telefoneresp = em.getReference(Telefoneresp.class, id);
                telefoneresp.getIdTelResp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefoneresp with id " + id + " no longer exists.", enfe);
            }
            Responsavel idResponsavel = telefoneresp.getIdResponsavel();
            if (idResponsavel != null) {
                idResponsavel.getTelefonerespCollection().remove(telefoneresp);
                idResponsavel = em.merge(idResponsavel);
            }
            em.remove(telefoneresp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefoneresp> findTelefonerespEntities() {
        return findTelefonerespEntities(true, -1, -1);
    }

    public List<Telefoneresp> findTelefonerespEntities(int maxResults, int firstResult) {
        return findTelefonerespEntities(false, maxResults, firstResult);
    }

    private List<Telefoneresp> findTelefonerespEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefoneresp.class));
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

    public Telefoneresp findTelefoneresp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefoneresp.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonerespCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefoneresp> rt = cq.from(Telefoneresp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
