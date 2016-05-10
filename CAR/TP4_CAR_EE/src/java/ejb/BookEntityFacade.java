/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean étend la classe AbstractBookFacade
 * @author jérôme
 */
@Stateless
@LocalBean
public class BookEntityFacade extends AbstractFacade<BookEntity> implements BookEntityFacadeLocalItf {
    
    @PersistenceContext(unitName = "HelloWorld_restPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookEntityFacade() {
        super(BookEntity.class);
    }
}