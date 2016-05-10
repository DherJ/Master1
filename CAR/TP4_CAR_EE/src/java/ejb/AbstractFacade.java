/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Classe abstraite contenant les services de la session Bean
 * @author jérôme
 * @param <T> : Nom de la classe sur laquelle implémenter ces services
 */
public abstract class AbstractFacade<T> {
    
    @SuppressWarnings("FieldMayBeFinal")
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Méthode qui permet d'ajouter un élément dans la base de donnée
     * @param entity : élement à ajouter
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Méthode qui permet de modifier un élément dans la base de donnée
     * @param entity : l'élément à modifier
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Méthode qui permet de supprimer un élément dans la base de donnée
     * @param entity : l'élément à supprimer
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Méthode qui permet de rechercher un élément dans la base de donnée
     * @param id : id de l'objet à chercher
     * @return Retourne un élément qui contient cet id
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Méthode qui permet de récupérer tous les élément de la base de donnée
     * @return Retourne une liste d'élément
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
        
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    /**
     * Méthode qui permet de récupérer tous les élements qui ont pour classe Panier
     * @return Une liste d'éléme,nt Panier
     */
    public List<Panier> getAllPanier() {
        
        CriteriaBuilder cb=getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Panier> cq=cb.createQuery(Panier.class);
        
        Root<Panier> p=cq.from(Panier.class);
        cq.select(p);
        TypedQuery<Panier> q = getEntityManager().createQuery(cq);
        List<Panier> AllPanier = q.getResultList();
        
        return AllPanier;
    }
    
    /**
     * Méthode qui permet d'ajouter un panier à la base de donnée
     * @param p : Le panier à ajouter
     */
    public void ajouter(Panier p) {
        //getEntityManager().persist(p);
        EntityManagerFactory emf;
        EntityManager  em;
        EntityTransaction transac ;
        emf = Persistence.createEntityManagerFactory("HelloWorld_restPU");
        em=emf.createEntityManager();
        transac = em.getTransaction();
        transac.begin();
        em.persist(p);
        transac.commit();
    }
}