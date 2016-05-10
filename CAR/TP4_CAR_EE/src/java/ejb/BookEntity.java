package ejb;

import java.io.Serializable;
import javax.ejb.Singleton;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * Classe qui représente un objet de base de donnée (une table)
 * @author Dhersin Jérôme - Knapik Christropher
 */
@Singleton
@Entity
@Table(name="Book")
public class BookEntity implements Serializable {
    
    @Id
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="annee")
    private int date;
    
    public BookEntity() {
    }
    
    public BookEntity(String _title,String _author,String _date){
        this.title = _title;
        if(_date.isEmpty())
            this.date = -1;
        else
            this.date = Integer.parseInt(_date);
        if(_author.isEmpty())
            this.author = null;
        else
            this.author = _author;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int year) {
        this.date = year;
    }
        
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (title != null ? title.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookEntity)) {
            return false;
        }
        BookEntity other = (BookEntity) object;
        if ((this.title == null && other.title != null) || (this.title != null && !this.title.equals(other.title))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ejb.BookEntity[ id=" + title + " ]";
    }
}