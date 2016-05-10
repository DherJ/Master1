/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Local;

/**
 * Interface listant l'ensemble des services de la session Bean
 * @author jérôme
 */
@Local
public interface BookEntityFacadeLocalItf {
    
    //public void init();

    void create(BookEntity bookEntity);

    void edit(BookEntity bookEntity);

    void remove(BookEntity bookEntity);

    BookEntity find(Object id);

    List<BookEntity> findAll();

    List<BookEntity> findRange(int[] range);

    int count();
    
}