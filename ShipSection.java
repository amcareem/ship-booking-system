import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Objects;

public class ShipSection {
    public final SeatClass sectionClass;
    public final String shipID;
    public final String shipCompanyID;
    public final int rows;
    public final int columns;
    private final ArrayList<Seat> seats = new ArrayList<>(1000);

    private final static int MAX_ROWS = 100;
    private final static int MAX_COLS = 10;

    public ShipSection(SeatClass sectionClass, String shipID, String shipCompanyID, int rows, int columns) {
        this.sectionClass = sectionClass;
        this.shipID = shipID;
        this.shipCompanyID = shipCompanyID;
        this.rows = rows;
        this.columns = columns;

        if (rows * columns > MAX_ROWS * MAX_COLS)
            throw new InvalidParameterException("Maximum number of rows-columns: " + MAX_ROWS + "-" + MAX_COLS);

        for (int i = 0; i < rows; i++) {
            char colChar = (char)((i % 11) + 97);
            for (int j = 0; j < columns; j++) {
                seats.add(new Seat(
                        String.format("%c-%d", colChar, (j % 10) + 1 )
                ));
            }

        }
//        for (int i = 1; i <= rows * columns; i++)
//            seats.add(new Seat(
//                        String.format("%c-%d", (char)((i % 11) + 96), i % 11)
//                    ));
    }

    public boolean hasAvailableSeats(){
        return seats.stream().anyMatch(seat -> !seat.booked);
    }

    public void bookSeat(int row, char column){
        var seatID = String.format("%c-%d", column, row);
        var seat = seats.stream().filter(
                s -> Objects.equals(
                        s.seatID,
                        seatID
                )).toList();

        if (seat.size() == 0)
            throw new InvalidParameterException("Invalid seat! No such seat found, " + seatID);

        seat.get(0).booked = true;
        System.out.println(seatID + " booked...");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipSection that = (ShipSection) o;
        return rows == that.rows && columns == that.columns && sectionClass.equals(that.sectionClass) && shipID.equals(that.shipID) && shipCompanyID.equals(that.shipCompanyID) && seats.equals(that.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionClass, shipID, shipCompanyID, rows, columns, seats);
    }

    @Override
    public String toString() {
        return "ShipSection{" +
                "sectionClass=" + sectionClass +
                ", shipID='" + shipID + '\'' +
                ", shipCompanyID='" + shipCompanyID + '\'' +
                ", rows=" + rows +
                ", columns=" + columns +
                ", seats=" + seats +
                '}';
    }
}
