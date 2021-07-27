package books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbnNumber;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;

    public Book(String isbnNumber, String title) {
        this.isbnNumber = isbnNumber;
        this.title = title;
    }
}
