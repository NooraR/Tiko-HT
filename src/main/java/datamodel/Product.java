package datamodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "product_status DEFAULT 'FREE' NOT NULL")
    @Enumerated(EnumType.STRING)
    private product_status status;

    @Basic
    @Column(name = "selling_price", nullable = true, precision = 0)
    private double sellingPrice;

    @Basic
    @Column(name = "purchase_price", nullable = true, precision = 0)
    private double purchasePrice;

    @ManyToOne
    @JoinColumn(name = "workid", referencedColumnName = "id")
    private Work work;

    private enum product_status {
        FREE, RESERVED, UNAVAILABLE;
    }

    public Product(){

        this.id = -1;
        this.work = null;
        this.status = product_status.FREE;
        this.sellingPrice = 0;
        this.purchasePrice = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Double.compare(product.sellingPrice, sellingPrice) == 0 &&
                Double.compare(product.purchasePrice, purchasePrice) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, sellingPrice, purchasePrice);
    }
}