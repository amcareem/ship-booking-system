import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;

public class SystemManager {
    private final  ArrayList<Harbor> harbors = new ArrayList<>();
    private final  ArrayList<ShippingCompany> shippingCompanies = new ArrayList<>();
    private final  ArrayList<Ship> ships = new ArrayList<>();
    private final  ArrayList<ShipSection> sections = new ArrayList<>();

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static SystemManager manager = null;
    private SystemManager() {}

    public static SystemManager getManager() {
        if (manager == null)
            manager = new SystemManager();

        return manager;
    }

    public void CreateHarbor(String harbor){
        if (harbor.length() != 3 || harbors.stream().anyMatch(h -> Objects.equals(h.name, harbor)))
            throw new InvalidParameterException("Harbor name must be unique & 3 characters long");

        harbors.add(new Harbor(harbor));
    }

    public void CreateShippingCompany(String company){
        if (company.length() > 5|| shippingCompanies.stream().anyMatch(c -> Objects.equals(c.name, company)))
            throw new InvalidParameterException("Shipping company name must be unique and < 6 characters.");

        shippingCompanies.add(new ShippingCompany(company));
    }

    public void CreateShip(String name, String orig, String dest, int year, int month, int day, String id){
        ships.add(
                new Ship(name, orig, dest, LocalDate.of(year, month, day), id)
        );
    }

    public void CreateSection(String shipCompanyID, String shipID, int rows, int cols, SeatClass s){
        var section = new ShipSection(s, shipID, shipCompanyID, rows, cols);
        sections.add(section);
    }

    public void FindAvailableShips(String orig, String dest){
        var availableShips = ships.stream().filter(
                s -> Objects.equals(s.originatingHarbor, orig) && Objects.equals(s.destinationHarbor, dest)
        ).toList();

        if (availableShips.size() > 0){
            for (var ship : availableShips) {
                var availableSections = sections.stream().filter(
                        section -> Objects.equals(section.shipID, ship.shipNumber)
                ).toList();

                if (availableSections.size() > 0){
                    System.out.println("Ship available: " + ship);
                }
            }
        }
        else
            System.out.println("No available ships from " + orig + " to" + dest);
    }

    public void BookSeat(String shipID, String shippingCompanyID, SeatClass seatClass, int row, char col){
        var filteredShips = ships.stream().filter(s -> Objects.equals(s.shipNumber, shipID)).toList();

        if (filteredShips.size() > 0){
            var ship = filteredShips.get(0);
            var filteredSections = sections.stream().filter(
                    s -> Objects.equals(s.shipID, ship.shipNumber) && s.sectionClass == seatClass && Objects.equals(s.shipCompanyID, shippingCompanyID)
            ).toList();

            if (filteredSections.size() > 0){
                var section = filteredSections.get(0);
                section.bookSeat(row, col);
            }
            else
                throw new InvalidParameterException("No such ship company or seat class.");
        }
        else {
            throw new InvalidParameterException("Invalid ship ID.");
        }
    }

    public void DisplaySystemDetails(){
        System.out.println("Companies:");
        for (var company : shippingCompanies)
            System.out.println(company);

        System.out.println("Harbors");
        for (var harbor : harbors)
            System.out.println(harbor);

        System.out.println("Ships:");
        for (var ship : ships)
            System.out.println(ship);

        System.out.println("Sections:");
        for (var section : sections) {
            System.out.println(section);
        }
    }

    public void Start(){
        System.out.println("Welcome to Ships Booking System");

        outer:
        while (true){
            int choice = Menu();

            try {
                switch (choice){
                    case 1:
                        System.out.print("Enter harbor name: ");
                        String harborName = reader.readLine();
                        CreateHarbor(harborName);
                        System.out.println("Harbor created");
                        break;
                    case 2:
                        System.out.print("Enter company name: ");
                        String companyName = reader.readLine();
                        CreateShippingCompany(companyName);
                        System.out.println("Shipping Company created.");
                        break;
                    case 3:
                        //var ship = new Ship()
                        System.out.print("Enter ship name: ");
                        var shipName = reader.readLine();
                        System.out.print("Enter ship ID: ");
                        var shipID = reader.readLine();
                        System.out.print("Enter originating harbor: ");
                        var originatingHarbor = reader.readLine();
                        System.out.print("Enter destination harbor: ");
                        var destinationHarbor = reader.readLine();
                        System.out.print("Enter departure date: ");
                        var departureDate = LocalDate.parse(reader.readLine());

                        CreateShip(shipName, originatingHarbor, destinationHarbor, departureDate.getYear(), departureDate.getMonth().getValue(), departureDate.getDayOfMonth(), shipID);
                        System.out.println("Ship created.");
                        break;
                    case 4:
                        System.out.print("Enter section class\n1. First class\n2. Second class\nThird class\n Choice: ");
                        var sectionClass = Integer.parseInt(reader.readLine());

                        SeatClass seatClass = switch (sectionClass) {
                            case 1 -> SeatClass.FIRST_CLASS;
                            case 2 -> SeatClass.SECOND_CLASS;
                            case 3 -> SeatClass.THIRD_CLASS;
                            default -> null;
                        };

                        System.out.print("Enter ship id: ");
                        shipID = reader.readLine();

                        System.out.print("Enter company id: ");
                        var companyID = reader.readLine();

                        System.out.print("Enter rows: ");
                        var rows = Integer.parseInt(reader.readLine());
                        System.out.print("Enter columns: ");
                        var columns = Integer.parseInt(reader.readLine());

                        CreateSection(companyID, shipID, rows, columns, seatClass);
                        System.out.println("Section created.");
                        break;
                    case 5:
                        System.out.print("Enter originating harbor: ");
                        var orgHarbor = reader.readLine();
                        System.out.print("Enter destination harbor: ");
                        var destHarbor = reader.readLine();

                        FindAvailableShips(orgHarbor, destHarbor);
                        break;
                    case 6:
                        System.out.print("Enter ship id: ");
                        shipID = reader.readLine();

                        System.out.print("Enter company id: ");
                        companyID = reader.readLine();

                        System.out.print("Enter section class\n1. First class\n2. Second class\nThird class\n Choice: ");
                        sectionClass = Integer.parseInt(reader.readLine());

                        seatClass = switch (sectionClass) {
                            case 1 -> SeatClass.FIRST_CLASS;
                            case 2 -> SeatClass.SECOND_CLASS;
                            case 3 -> SeatClass.THIRD_CLASS;
                            default -> null;
                        };

                        System.out.print("Enter row: ");
                        var row = Integer.parseInt(reader.readLine());
                        System.out.print("Enter columns: ");
                        var column = reader.readLine();

                        BookSeat(shipID, companyID, seatClass, row, column.toCharArray()[0]);
                        System.out.println("Seat booked");
                        break;
                    case 7:
                        DisplaySystemDetails();
                        break;
                    case 8:
                        break outer;

                }
            }
            catch (InvalidParameterException | IOException e){
                System.err.println(e.getMessage());
            }
        }
    }

    private int Menu(){
        while (true){
            System.out.println(
                    "\nEnter\n" +
                            "1. Create Harbor\n" +
                            "2. Create Shipping Company\n" +
                            "3. Create Ship\n" +
                            "4. Create Section\n" +
                            "5. Find Available Ships\n" +
                            "6. Book Seat\n" +
                            "7. Display System Details\n" +
                            "8. Exit\n\n" +
                            "Choice: "
            );

            try {
                int choice = Integer.parseInt(reader.readLine());
                if (choice < 0 || choice > 8)
                    throw new InputMismatchException();

                return choice;
            }
            catch (InputMismatchException | NumberFormatException e){
                System.out.println("Invalid input!");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
