package DB;

import java.sql.Date;


public class Order {

    private enum order_status {
        waiting, processed, sent
    }

    private int id;
    private Date orderdate;
    private order_status status;
    private int user;

    public Order(int id, Date orderdate, order_status status, int user){

        this.id = id;
        this.orderdate = orderdate;
        this.status = status;
        this.user = user;
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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

}
