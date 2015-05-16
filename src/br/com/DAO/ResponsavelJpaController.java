/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.DAO;

import br.com.DAO.exceptions.NonexistentEntityException;
import br.com.DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.Classes.Telefoneresp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.Classes.Responsavel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class ResponsavelJpaController implements Serializable {

    public ResponsavelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Responsavel responsavel) throws PreexistingEntityException, Exception {
        if (responsavel.getTelefonerespCollection() == null) {
            responsavel.setTelefonerespCollection(new ArrayList<Telefoneresp>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Telefoneresp> attachedTelefonerespCollection = new ArrayList<Telefoneresp>();
            for (Telefoneresp telefonerespCollectionTelefonerespToAttach : responsavel.getTelefonerespCollection()) {
                telefonerespCollectionTelefonerespToAttach = em.getReference(telefonerespCollectionTelefonerespToAttach.getClass(), telefonerespCollectionTelefonerespToAttach.getIdTelResp());
                attachedTelefonerespCollection.add(telefonerespCollectionTelefonerespToAttach);
            }
            responsavel.setTelefonerespCollection(attachedTelefonerespCollection);
            em.persist(responsavel);
            for (Telefoneresp telefonerespCollectionTelefoneresp : responsavel.getTelefonerespCollection()) {
                Responsavel oldIdResponsavelOfTelefonerespCollectionTelefoneresp = telefonerespCollectionTelefoneresp.getIdResponsavel();
                telefonerespCollectionTelefoneresp.setIdResponsavel(responsavel);
                telefonerespCollectionTelefoneresp = em.merge(telefonerespCollectionTelefoneresp);
                if (oldIdResponsavelOfTelefonerespCollectionTelefoneresp != null) {
                    oldIdResponsavelOfTelefonerespCollectionTelefoneresp.getTelefonerespCollection().remove(telefonerespCollectionTelefoneresp);
                    oldIdResponsavelOfTelefonerespCollectionTelefoneresp = em.merge(oldIdResponsavelOfTelefonerespCollectionTelefoneresp);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResponsavel(responsavel.getIdResponsavel()) != null) {
                throw new PreexistingEntityException("Responsavel " + responsavel + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Responsavel responsavel) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Responsavel persistentResponsavel = em.find(Responsavel.class, responsavel.getIdResponsavel());
            Collection<Telefoneresp> telefonerespCollectionOld = persistentResponsavel.getTelefonerespCollection();
            Collection<Telefoneresp> telefonerespCollectionNew = responsavel.getTelefonerespCollection();
            Collection<Telefoneresp> attachedTelefonerespCollectionNew = new ArrayList<Telefoneresp>();
            for (Telefoneresp telefonerespCollectionNewTelefonerespToAttach : telefonerespCollectionNew) {
                telefonerespCollectionNewTelefonerespToAttach = em.getReference(telefonerespCollectionNewTelefonerespToAttach.getClass(), telefonerespCollectionNewTelefonerespToAttach.getIdTelResp());
                attachedTelefonerespCollectionNew.add(telefonerespCollectionNewTelefonerespToAttach);
            }
            telefonerespCollectionNew = attachedTelefonerespCollectionNew;
            responsavel.setTelefonerespCollection(telefonerespCollectionNew);
            responsavel = em.merge(responsavel);
            for (Telefoneresp telefonerespCollectionOldTelefoneresp : telefonerespCollectionOld) {
                if (!telefonerespCollectionNew.contains(telefonerespCollectionOldTelefoneresp)) {
                    telefonerespCollectionOldTelefoneresp.setIdResponsavel(null);
                    telefonerespCollectionOldTelefoneresp = em.merge(telefonerespCollectionOldTelefoneresp);
                }
            }
            for (Telefoneresp telefonerespCollectionNewTelefoneresp : telefonerespCollectionNew) {
                if (!telefonerespCollectionOld.contains(telefonerespCollectionNewTelefoneresp)) {
                    Responsavel oldIdResponsavelOfTelefonerespCollectionNewTelefoneresp = telefonerespCollectionNewTelefoneresp.getIdResponsavel();
                    telefonerespCollectionNewTelefoneresp.setIdResponsavel(responsavel);
                    telefonerespCollectionNewTelefoneresp = em.merge(telefonerespCollectionNewTelefoneresp);
                    if (oldIdResponsavelOfTelefonerespCollectionNewTelefoneresp != null && !oldIdResponsavelOfTelefonerespCollectionNewTelefoneresp.equals(responsavel)) {
                        oldIdResponsavelOfTelefonerespCollectionNewTelefoneresp.getTelefonerespCollection().remove(telefonerespCollectionNewTelefoneresp);
                        oldIdResponsavelOfTelefonerespCollectionNewTelefoneresp = em.merge(oldIdResponsavelOfTelefonerespCollectionNewTelefoneresp);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = responsavel.getIdResponsavel();
                if (findResponsavel(id) == null) {
                    throw new NonexistentEntityException("The responsavel with id " + id + " no longer exists.");
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
            Responsavel responsavel;
            try {
                responsavel = em.getReference(Responsavel.class, id);
                responsavel.getIdResponsavel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The responsavel with id " + id + " no longer exists.", enfe);
            }
            Collection<Telefoneresp> telefonerespCollection = responsavel.getTelefonerespCollection();
            for (Telefoneresp telefonerespCollectionTelefoneresp : telefonerespCollection) {
                telefonerespCollectionTelefoneresp.setIdResponsavel(null);
                telefonerespCollectionTelefoneresp = em.merge(telefonerespCollectionTelefoneresp);
            }
            em.remove(responsavel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Responsavel> findResponsavelEntities() {
        return findResponsavelEntities(true, -1, -1);
    }

    public List<Responsavel> findResponsavelEntities(int maxResults, int firstResult) {
        return findResponsavelEntities(false, maxResults, firstResult);
    }

    private List<Responsavel> findResponsavelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Responsavel.class));
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

    public Responsavel findResponsavel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Responsavel.class, id);
        } finally {
            em.close();
        }
    }

    public int getResponsavelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Responsavel> rt = cq.from(Responsavel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
