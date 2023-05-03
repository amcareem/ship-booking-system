public class Seat {
    public final String  seatID;
    public boolean booked = false;

    public Seat(String seatID) {
        this.seatID = seatID;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatID='" + seatID + '\'' +
                ", booked=" + booked +
                '}';
    }
}