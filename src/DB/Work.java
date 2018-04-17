package DB;

public class Work {

    private int id;
    private String author;
    private String name;
    private String isbn;
    private int published;
    private String genre;
    private String type;
    private double weight;

    public Work(int id, String author, String name, String isbn, int published, String genre, String type, double weight){

        this.id = id;
        this.author = author;
        this.name = name;
        this.isbn = isbn;
        this.published = published;
        this.genre = genre;
        this.type = type;
        this.weight = weight;
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

}