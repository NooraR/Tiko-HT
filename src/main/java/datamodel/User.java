package datamodel;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "useraccount")
public class User {

    @Id
    @SequenceGenerator(name = "user_id_seq", schema = "central", sequenceName = "useraccount_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    @Expose
    private int id;

    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    @Expose
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    @Expose
    private String lastName;

    @Basic
    @Column(name = "address", nullable = true, length = 50)
    @Expose
    private String address;

    @Basic
    @Column(name = "email", nullable = false, length = 50)
    @Expose
    private String email;

    @Basic
    @Column(name = "password", nullable = true, length = 300)
    @Expose(serialize = false)
    private String password;

    @Basic
    @Column(name = "phone_number", nullable = true, length = 20)
    @Expose
    private String phoneNumber;

    public User(){
        this.id = -1;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.email = null;
        this.phoneNumber = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(address, user.address) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, address, email, password, phoneNumber);
    }
}