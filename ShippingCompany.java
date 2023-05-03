import java.util.Objects;

public class ShippingCompany {
    public final String name;

    public ShippingCompany(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ShippingCompany{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShippingCompany that = (ShippingCompany) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
