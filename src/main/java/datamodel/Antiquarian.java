package datamodel;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "antiquarian", schema = "central")
public class Antiquarian {

    @Id
    @SequenceGenerator(name = "antiquarian_id_seq", schema = "central",sequenceName = "antiquarian_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "antiquarian_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    @Expose
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    @Expose
    private String name;

    @Basic
    @Column(name = "address", nullable = true, length = 50)
    @Expose
    private String address;

    @Basic
    @Column(name = "web", nullable = true, length = 50)
    @Expose
    private String web;

    @Basic
    @Column(name = "db_schema", nullable = true, length = 40)
    private String dbIdentifier;

    public Antiquarian() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getDbIdentifier() {
        return dbIdentifier;
    }

    public void setDbIdentifier(String dbIdentifier) {
        this.dbIdentifier = dbIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antiquarian that = (Antiquarian) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(web, that.web);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, address, web);
    }
}
