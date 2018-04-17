package DB;

public class Product {

    private int id;
    private String isbn;
    private String order;

    private product_status status;
    private double sellingPrice;
    private double purchasePrice;

    public Product(int id, String isbn, String order, product_status status, double sellingPrice, double purchasePrice){

        this.id = id;
        this.isbn = isbn;
        this.order = order;
        this.status = status;
        this.sellingPrice = sellingPrice;
        this.purchasePrice = purchasePrice;
    }

    private enum product_status {
        free, taken, removed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public product_status getStatus() {
        return status;
    }

    public void setStatus(product_status status) {
        this.status = status;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

}