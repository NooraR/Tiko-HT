package datamodel;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;

    public User(int Id, String Firstname, String Lastname, String Address, String Email, String Phonenumber){
        id = Id;
        firstName = Firstname;
        lastName = Lastname;
        address = Address;
        email = Email;
        phoneNumber = Phonenumber;
    }

    public User(){
        this.id = -1;
        this.firstName = "";
        this.lastName = "";
        this.address = "";
        this.email = "";
        this.phoneNumber = "";

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}