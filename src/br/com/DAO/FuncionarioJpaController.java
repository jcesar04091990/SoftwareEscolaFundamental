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
import br.com.Classes.Telfuncionario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.Classes.Colaborador;
import br.com.Classes.Aluno;
import br.com.Classes.Funcionario;
import br.com.Classes.Professor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) throws PreexistingEntityException, Exception {
        if (funcionario.getTelfuncionarioCollection() == null) {
            funcionario.setTelfuncionarioCollection(new ArrayList<Telfuncionario>());
        }
        if (funcionario.getColaboradorCollection() == null) {
            funcionario.setColaboradorCollection(new ArrayList<Colaborador>());
        }
        if (funcionario.getAlunoCollection() == null) {
            funcionario.setAlunoCollection(new ArrayList<Aluno>());
        }
        if (funcionario.getProfessorCollection() == null) {
            funcionario.setProfessorCollection(new ArrayList<Professor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Telfuncionario> attachedTelfuncionarioCollection = new ArrayList<Telfuncionario>();
            for (Telfuncionario telfuncionarioCollectionTelfuncionarioToAttach : funcionario.getTelfuncionarioCollection()) {
                telfuncionarioCollectionTelfuncionarioToAttach = em.getReference(telfuncionarioCollectionTelfuncionarioToAttach.getClass(), telfuncionarioCollectionTelfuncionarioToAttach.getIdTelFunc());
                attachedTelfuncionarioCollection.add(telfuncionarioCollectionTelfuncionarioToAttach);
            }
            funcionario.setTelfuncionarioCollection(attachedTelfuncionarioCollection);
            Collection<Colaborador> attachedColaboradorCollection = new ArrayList<Colaborador>();
            for (Colaborador colaboradorCollectionColaboradorToAttach : funcionario.getColaboradorCollection()) {
                colaboradorCollectionColaboradorToAttach = em.getReference(colaboradorCollectionColaboradorToAttach.getClass(), colaboradorCollectionColaboradorToAttach.getIdColaborador());
                attachedColaboradorCollection.add(colaboradorCollectionColaboradorToAttach);
            }
            funcionario.setColaboradorCollection(attachedColaboradorCollection);
            Collection<Aluno> attachedAlunoCollection = new ArrayList<Aluno>();
            for (Aluno alunoCollectionAlunoToAttach : funcionario.getAlunoCollection()) {
                alunoCollectionAlunoToAttach = em.getReference(alunoCollectionAlunoToAttach.getClass(), alunoCollectionAlunoToAttach.getIdAluno());
                attachedAlunoCollection.add(alunoCollectionAlunoToAttach);
            }
            funcionario.setAlunoCollection(attachedAlunoCollection);
            Collection<Professor> attachedProfessorCollection = new ArrayList<Professor>();
            for (Professor professorCollectionProfessorToAttach : funcionario.getProfessorCollection()) {
                professorCollectionProfessorToAttach = em.getReference(professorCollectionProfessorToAttach.getClass(), professorCollectionProfessorToAttach.getIdProfessor());
                attachedProfessorCollection.add(professorCollectionProfessorToAttach);
            }
            funcionario.setProfessorCollection(attachedProfessorCollection);
            em.persist(funcionario);
            for (Telfuncionario telfuncionarioCollectionTelfuncionario : funcionario.getTelfuncionarioCollection()) {
                Funcionario oldCodFuncOfTelfuncionarioCollectionTelfuncionario = telfuncionarioCollectionTelfuncionario.getCodFunc();
                telfuncionarioCollectionTelfuncionario.setCodFunc(funcionario);
                telfuncionarioCollectionTelfuncionario = em.merge(telfuncionarioCollectionTelfuncionario);
                if (oldCodFuncOfTelfuncionarioCollectionTelfuncionario != null) {
                    oldCodFuncOfTelfuncionarioCollectionTelfuncionario.getTelfuncionarioCollection().remove(telfuncionarioCollectionTelfuncionario);
                    oldCodFuncOfTelfuncionarioCollectionTelfuncionario = em.merge(oldCodFuncOfTelfuncionarioCollectionTelfuncionario);
                }
            }
            for (Colaborador colaboradorCollectionColaborador : funcionario.getColaboradorCollection()) {
                Funcionario oldCodFuncOfColaboradorCollectionColaborador = colaboradorCollectionColaborador.getCodFunc();
                colaboradorCollectionColaborador.setCodFunc(funcionario);
                colaboradorCollectionColaborador = em.merge(colaboradorCollectionColaborador);
                if (oldCodFuncOfColaboradorCollectionColaborador != null) {
                    oldCodFuncOfColaboradorCollectionColaborador.getColaboradorCollection().remove(colaboradorCollectionColaborador);
                    oldCodFuncOfColaboradorCollectionColaborador = em.merge(oldCodFuncOfColaboradorCollectionColaborador);
                }
            }
            for (Aluno alunoCollectionAluno : funcionario.getAlunoCollection()) {
                Funcionario oldCodFuncOfAlunoCollectionAluno = alunoCollectionAluno.getCodFunc();
                alunoCollectionAluno.setCodFunc(funcionario);
                alunoCollectionAluno = em.merge(alunoCollectionAluno);
                if (oldCodFuncOfAlunoCollectionAluno != null) {
                    oldCodFuncOfAlunoCollectionAluno.getAlunoCollection().remove(alunoCollectionAluno);
                    oldCodFuncOfAlunoCollectionAluno = em.merge(oldCodFuncOfAlunoCollectionAluno);
                }
            }
            for (Professor professorCollectionProfessor : funcionario.getProfessorCollection()) {
                Funcionario oldCodFuncOfProfessorCollectionProfessor = professorCollectionProfessor.getCodFunc();
                professorCollectionProfessor.setCodFunc(funcionario);
                professorCollectionProfessor = em.merge(professorCollectionProfessor);
                if (oldCodFuncOfProfessorCollectionProfessor != null) {
                    oldCodFuncOfProfessorCollectionProfessor.getProfessorCollection().remove(professorCollectionProfessor);
                    oldCodFuncOfProfessorCollectionProfessor = em.merge(oldCodFuncOfProfessorCollectionProfessor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionario(funcionario.getCodFunc()) != null) {
                throw new PreexistingEntityException("Funcionario " + funcionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getCodFunc());
            Collection<Telfuncionario> telfuncionarioCollectionOld = persistentFuncionario.getTelfuncionarioCollection();
            Collection<Telfuncionario> telfuncionarioCollectionNew = funcionario.getTelfuncionarioCollection();
            Collection<Colaborador> colaboradorCollectionOld = persistentFuncionario.getColaboradorCollection();
            Collection<Colaborador> colaboradorCollectionNew = funcionario.getColaboradorCollection();
            Collection<Aluno> alunoCollectionOld = persistentFuncionario.getAlunoCollection();
            Collection<Aluno> alunoCollectionNew = funcionario.getAlunoCollection();
            Collection<Professor> professorCollectionOld = persistentFuncionario.getProfessorCollection();
            Collection<Professor> professorCollectionNew = funcionario.getProfessorCollection();
            Collection<Telfuncionario> attachedTelfuncionarioCollectionNew = new ArrayList<Telfuncionario>();
            for (Telfuncionario telfuncionarioCollectionNewTelfuncionarioToAttach : telfuncionarioCollectionNew) {
                telfuncionarioCollectionNewTelfuncionarioToAttach = em.getReference(telfuncionarioCollectionNewTelfuncionarioToAttach.getClass(), telfuncionarioCollectionNewTelfuncionarioToAttach.getIdTelFunc());
                attachedTelfuncionarioCollectionNew.add(telfuncionarioCollectionNewTelfuncionarioToAttach);
            }
            telfuncionarioCollectionNew = attachedTelfuncionarioCollectionNew;
            funcionario.setTelfuncionarioCollection(telfuncionarioCollectionNew);
            Collection<Colaborador> attachedColaboradorCollectionNew = new ArrayList<Colaborador>();
            for (Colaborador colaboradorCollectionNewColaboradorToAttach : colaboradorCollectionNew) {
                colaboradorCollectionNewColaboradorToAttach = em.getReference(colaboradorCollectionNewColaboradorToAttach.getClass(), colaboradorCollectionNewColaboradorToAttach.getIdColaborador());
                attachedColaboradorCollectionNew.add(colaboradorCollectionNewColaboradorToAttach);
            }
            colaboradorCollectionNew = attachedColaboradorCollectionNew;
            funcionario.setColaboradorCollection(colaboradorCollectionNew);
            Collection<Aluno> attachedAlunoCollectionNew = new ArrayList<Aluno>();
            for (Aluno alunoCollectionNewAlunoToAttach : alunoCollectionNew) {
                alunoCollectionNewAlunoToAttach = em.getReference(alunoCollectionNewAlunoToAttach.getClass(), alunoCollectionNewAlunoToAttach.getIdAluno());
                attachedAlunoCollectionNew.add(alunoCollectionNewAlunoToAttach);
            }
            alunoCollectionNew = attachedAlunoCollectionNew;
            funcionario.setAlunoCollection(alunoCollectionNew);
            Collection<Professor> attachedProfessorCollectionNew = new ArrayList<Professor>();
            for (Professor professorCollectionNewProfessorToAttach : professorCollectionNew) {
                professorCollectionNewProfessorToAttach = em.getReference(professorCollectionNewProfessorToAttach.getClass(), professorCollectionNewProfessorToAttach.getIdProfessor());
                attachedProfessorCollectionNew.add(professorCollectionNewProfessorToAttach);
            }
            professorCollectionNew = attachedProfessorCollectionNew;
            funcionario.setProfessorCollection(professorCollectionNew);
            funcionario = em.merge(funcionario);
            for (Telfuncionario telfuncionarioCollectionOldTelfuncionario : telfuncionarioCollectionOld) {
                if (!telfuncionarioCollectionNew.contains(telfuncionarioCollectionOldTelfuncionario)) {
                    telfuncionarioCollectionOldTelfuncionario.setCodFunc(null);
                    telfuncionarioCollectionOldTelfuncionario = em.merge(telfuncionarioCollectionOldTelfuncionario);
                }
            }
            for (Telfuncionario telfuncionarioCollectionNewTelfuncionario : telfuncionarioCollectionNew) {
                if (!telfuncionarioCollectionOld.contains(telfuncionarioCollectionNewTelfuncionario)) {
                    Funcionario oldCodFuncOfTelfuncionarioCollectionNewTelfuncionario = telfuncionarioCollectionNewTelfuncionario.getCodFunc();
                    telfuncionarioCollectionNewTelfuncionario.setCodFunc(funcionario);
                    telfuncionarioCollectionNewTelfuncionario = em.merge(telfuncionarioCollectionNewTelfuncionario);
                    if (oldCodFuncOfTelfuncionarioCollectionNewTelfuncionario != null && !oldCodFuncOfTelfuncionarioCollectionNewTelfuncionario.equals(funcionario)) {
                        oldCodFuncOfTelfuncionarioCollectionNewTelfuncionario.getTelfuncionarioCollection().remove(telfuncionarioCollectionNewTelfuncionario);
                        oldCodFuncOfTelfuncionarioCollectionNewTelfuncionario = em.merge(oldCodFuncOfTelfuncionarioCollectionNewTelfuncionario);
                    }
                }
            }
            for (Colaborador colaboradorCollectionOldColaborador : colaboradorCollectionOld) {
                if (!colaboradorCollectionNew.contains(colaboradorCollectionOldColaborador)) {
                    colaboradorCollectionOldColaborador.setCodFunc(null);
                    colaboradorCollectionOldColaborador = em.merge(colaboradorCollectionOldColaborador);
                }
            }
            for (Colaborador colaboradorCollectionNewColaborador : colaboradorCollectionNew) {
                if (!colaboradorCollectionOld.contains(colaboradorCollectionNewColaborador)) {
                    Funcionario oldCodFuncOfColaboradorCollectionNewColaborador = colaboradorCollectionNewColaborador.getCodFunc();
                    colaboradorCollectionNewColaborador.setCodFunc(funcionario);
                    colaboradorCollectionNewColaborador = em.merge(colaboradorCollectionNewColaborador);
                    if (oldCodFuncOfColaboradorCollectionNewColaborador != null && !oldCodFuncOfColaboradorCollectionNewColaborador.equals(funcionario)) {
                        oldCodFuncOfColaboradorCollectionNewColaborador.getColaboradorCollection().remove(colaboradorCollectionNewColaborador);
                        oldCodFuncOfColaboradorCollectionNewColaborador = em.merge(oldCodFuncOfColaboradorCollectionNewColaborador);
                    }
                }
            }
            for (Aluno alunoCollectionOldAluno : alunoCollectionOld) {
                if (!alunoCollectionNew.contains(alunoCollectionOldAluno)) {
                    alunoCollectionOldAluno.setCodFunc(null);
                    alunoCollectionOldAluno = em.merge(alunoCollectionOldAluno);
                }
            }
            for (Aluno alunoCollectionNewAluno : alunoCollectionNew) {
                if (!alunoCollectionOld.contains(alunoCollectionNewAluno)) {
                    Funcionario oldCodFuncOfAlunoCollectionNewAluno = alunoCollectionNewAluno.getCodFunc();
                    alunoCollectionNewAluno.setCodFunc(funcionario);
                    alunoCollectionNewAluno = em.merge(alunoCollectionNewAluno);
                    if (oldCodFuncOfAlunoCollectionNewAluno != null && !oldCodFuncOfAlunoCollectionNewAluno.equals(funcionario)) {
                        oldCodFuncOfAlunoCollectionNewAluno.getAlunoCollection().remove(alunoCollectionNewAluno);
                        oldCodFuncOfAlunoCollectionNewAluno = em.merge(oldCodFuncOfAlunoCollectionNewAluno);
                    }
                }
            }
            for (Professor professorCollectionOldProfessor : professorCollectionOld) {
                if (!professorCollectionNew.contains(professorCollectionOldProfessor)) {
                    professorCollectionOldProfessor.setCodFunc(null);
                    professorCollectionOldProfessor = em.merge(professorCollectionOldProfessor);
                }
            }
            for (Professor professorCollectionNewProfessor : professorCollectionNew) {
                if (!professorCollectionOld.contains(professorCollectionNewProfessor)) {
                    Funcionario oldCodFuncOfProfessorCollectionNewProfessor = professorCollectionNewProfessor.getCodFunc();
                    professorCollectionNewProfessor.setCodFunc(funcionario);
                    professorCollectionNewProfessor = em.merge(professorCollectionNewProfessor);
                    if (oldCodFuncOfProfessorCollectionNewProfessor != null && !oldCodFuncOfProfessorCollectionNewProfessor.equals(funcionario)) {
                        oldCodFuncOfProfessorCollectionNewProfessor.getProfessorCollection().remove(professorCollectionNewProfessor);
                        oldCodFuncOfProfessorCollectionNewProfessor = em.merge(oldCodFuncOfProfessorCollectionNewProfessor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionario.getCodFunc();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getCodFunc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            Collection<Telfuncionario> telfuncionarioCollection = funcionario.getTelfuncionarioCollection();
            for (Telfuncionario telfuncionarioCollectionTelfuncionario : telfuncionarioCollection) {
                telfuncionarioCollectionTelfuncionario.setCodFunc(null);
                telfuncionarioCollectionTelfuncionario = em.merge(telfuncionarioCollectionTelfuncionario);
            }
            Collection<Colaborador> colaboradorCollection = funcionario.getColaboradorCollection();
            for (Colaborador colaboradorCollectionColaborador : colaboradorCollection) {
                colaboradorCollectionColaborador.setCodFunc(null);
                colaboradorCollectionColaborador = em.merge(colaboradorCollectionColaborador);
            }
            Collection<Aluno> alunoCollection = funcionario.getAlunoCollection();
            for (Aluno alunoCollectionAluno : alunoCollection) {
                alunoCollectionAluno.setCodFunc(null);
                alunoCollectionAluno = em.merge(alunoCollectionAluno);
            }
            Collection<Professor> professorCollection = funcionario.getProfessorCollection();
            for (Professor professorCollectionProfessor : professorCollection) {
                professorCollectionProfessor.setCodFunc(null);
                professorCollectionProfessor = em.merge(professorCollectionProfessor);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
