package datamodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "userorder", schema = "central", catalog = "tikoht")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userorder_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Basic
    @Column(name = "orderdate", nullable = false)
    private Date orderdate;

    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "order_status DEFAULT 'WAITING' NOT NULL")
    @Enumerated(EnumType.STRING)
    private order_status status;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Product> productList;

    private enum order_status {
        WAITING, PROCESSED, COMPLETE
    }

    public Order(){

        this.id = -1;
        this.orderdate = null;
        this.status = null;
        productList = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public order_status getStatus() {
        return status;
    }

    public void setStatus(order_status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(orderdate, order.orderdate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderdate);
    }
}
