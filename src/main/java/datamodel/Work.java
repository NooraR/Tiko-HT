package datamodel;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Work {

    @Id
    @SequenceGenerator(name = "work_id_seq", sequenceName = "work_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Basic
    @Column(name = "author", nullable = false, length = 50)
    private String author;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "isbn", nullable = true, length = 20)
    private String isbn;

    @Basic
    @Column(name = "published", nullable = true)
    private int published;

    @Basic
    @Column(name = "genre", nullable = true, length = 50)
    private String genre;

    @Basic
    @Column(name = "type", nullable = true, length = 50)
    private String type;

    @Basic
    @Column(name = "weight", nullable = false, precision = 0)
    private double weight;

    @Formula("(SELECT COUNT(product.id) FROM product WHERE product.workid = id)")
    private int balance;


    public Work(){
        this.id = -1;
        this.author = null;
        this.name = null;
        this.isbn = null;
        this.published = 0;
        this.genre = null;
        this.type = null;
        this.weight = 0;
        this.balance = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Work work = (Work) o;
        return id == work.id &&
                published == work.published &&
                Double.compare(work.weight, weight) == 0 &&
                Objects.equals(author, work.author) &&
                Objects.equals(name, work.name) &&
                Objects.equals(isbn, work.isbn) &&
                Objects.equals(genre, work.genre) &&
                Objects.equals(type, work.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, author, name, isbn, published, genre, type, weight);
    }
}