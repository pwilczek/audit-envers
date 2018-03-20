package name.wilu.auditenvers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Audited @Entity
public class User {
    //
    @Id public UUID id;
    @Column(unique = true) public String name;
    public boolean active = true;
    @OneToMany(mappedBy = "user")
    public List<Book> books = new ArrayList<>();

    public User addBook(Book book) {
        book.user = this;
        books.add(book);
        return this;
    }

    @Entity @Audited
    @Table(name = "book")
    public static class Book {
        //
        @Id public UUID id;
        public String title;
        @JsonIgnore @ManyToOne public User user;
    }
}
