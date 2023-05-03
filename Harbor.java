import java.util.Objects;

public class Harbor {
    public final String name;

    public Harbor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Harbor{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Harbor harbor = (Harbor) o;
        return name.equals(harbor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
