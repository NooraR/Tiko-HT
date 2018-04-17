package datamodel;

public class Antiquarian {

    private int id;
    private String name;
    private String address;
    private String web;

    public Antiquarian(int id, String name, String address, String web){

        this.id = id;
        this.name = name;
        this.address = address;
        this.web = web;
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
}
