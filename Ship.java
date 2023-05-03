import java.time.LocalDate;
import java.util.Objects;

public class Ship {
    public final String name;
    public final String originatingHarbor;
    public final String destinationHarbor;
    public final String shipNumber;
    public final LocalDate departureDate;

    public Ship(String name, String originatingHarbor, String destinationHarbor, LocalDate departureDate, String id) {
        this.name = name;
        this.originatingHarbor = originatingHarbor;
        this.destinationHarbor = destinationHarbor;
        this.departureDate = departureDate;
        this.shipNumber = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return name.equals(ship.name) && originatingHarbor.equals(ship.originatingHarbor) && destinationHarbor.equals(ship.destinationHarbor) && shipNumber.equals(ship.shipNumber) && departureDate.equals(ship.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, originatingHarbor, destinationHarbor, shipNumber, departureDate);
    }

    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                ", originatingHarbor='" + originatingHarbor + '\'' +
                ", destinationHarbor='" + destinationHarbor + '\'' +
                ", shipNumber=" + shipNumber +
                ", departureDate=" + departureDate +
                '}';
    }
}