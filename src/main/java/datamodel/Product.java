package datamodel;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Product {

    public static final String FREE = "FREE";
    public static final String RESERVED = "RESERVED";
    public static final String UNAVAILABLE = "UNAVAILABLE";

    @Id
    @SequenceGenerator(name = "product_id_seq", schema = "central", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    @Expose
    private int id;

    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'FREE' NOT NULL")
    @Expose(serialize = false)
    private String status;

    @Basic
    @Column(name = "selling_price", nullable = true, precision = 0)
    @Expose
    private double sellingPrice;

    @Basic
    @Column(name = "purchase_price", nullable = true, precision = 0)
    @Expose(serialize = false)
    private double purchasePrice;

    @ManyToOne
    @JoinColumn(name = "workid", referencedColumnName = "id")
    @Expose(deserialize = true, serialize = false)
    private Work work;

    @ManyToOne
    @JoinColumn(name = "orderid", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "antiquary_id", referencedColumnName = "id")
    @Expose
    private Antiquarian antiquary;

    @Transient
    @Expose
    private int workid;

    public Product(){

        this.id = -1;
        this.work = null;
        this.status = null;
        this.sellingPrice = 0;
        this.purchasePrice = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Antiquarian getAntiquary() {
        return antiquary;
    }

    public void setAntiquary(Antiquarian antiquary) {
        this.antiquary = antiquary;
    }

    public int getWorkId() {
        return workid;
    }

    public void setWorkId(int workid) {
        this.workid = workid;
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