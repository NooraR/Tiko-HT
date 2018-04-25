package datamodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "postage", schema = "central")
public class Postage {

    @Id
    @Column(name = "weight", updatable = true, nullable = false)
    private double weight;

    @Basic
    @Column(name = "fee", updatable = true, nullable = false)
    private double fee;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postage postage = (Postage) o;
        return Double.compare(weight, postage.weight) == 0 &&
                Double.compare(fee, postage.fee) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, fee);
    }

}
