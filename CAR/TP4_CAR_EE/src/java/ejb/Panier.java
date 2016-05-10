package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Classe correspondant à un élément de la base de donnée
 * @author jerome
 */
@Entity
public class Panier implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id; 
    @OneToMany
    private Collection<BookEntity> panier;
    
    
    public Panier(){
        panier=new ArrayList<BookEntity>();
    }
    
    /**
     * test si le panier est vide
     * @return 
     */
    public boolean isEmpty(){
        return panier.isEmpty();
    }
    
    /**
     * ajoute un livre au panier
     * @param l 
     */
    public void add(BookEntity l){
        panier.add(l);
    }
 
    /**
     * retourne la liste du panier en code HTML
     * @return 
     */
    public String getHtmlCode(){
        String retour ="Panier :";
        if(panier.isEmpty())
            return retour+"vide";
        for(BookEntity l:panier)
            retour+="<br>"+l.toString();
        return retour;
    }

    /**
     * vide le panier
     */
    public void reset() {
        panier.clear();
    }
}
