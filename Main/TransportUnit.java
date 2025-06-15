package Main;

public class TransportUnit extends CityResource {
    private String fuelType;
    private int capacity;
    private int currentPassengers;

    public TransportUnit(String resourceID, String location, String status,int x, int y, String fuelType, int capacity,
            int currentPassengers) {
        super(resourceID, location, status, x, y);

        this.fuelType = fuelType;
        this.capacity = capacity;
        this.currentPassengers = currentPassengers;
    }


    @Override
    public double calculateMaintenanceCost() {
        // Example calculation: base cost + cost per passenger + cost per capacity
        double baseCost = 1000.0;
        double passengerCost = 50.0 * currentPassengers;
        double capacityCost = 20.0 * capacity;

        // Additional cost based on fuel type
        double fuelTypeCost = 0.0;
        if ("Diesel".equalsIgnoreCase(fuelType)) {
            fuelTypeCost = 200.0;
        } else if ("Petrol".equalsIgnoreCase(fuelType)) {
            fuelTypeCost = 150.0;
        } else if ("Electric".equalsIgnoreCase(fuelType)) {
            fuelTypeCost = 100.0;
        }

        return baseCost + passengerCost + capacityCost + fuelTypeCost;
    }



    @Override
    public String generateUserReport() {
        return String.format(
            "Transport Unit Report:\n" +
            "Resource ID: %s\n" +
            "Location: %s\n" +
            "Status: %s\n" +
            "Fuel Type: %s\n" +
            "Capacity: %d\n" +
            "Current Passengers: %d\n" +
            "Maintenance Cost: %.2f",
            getResourceID(),
            getLocation(),
            getStatus(),
            fuelType,
            capacity,
            currentPassengers,
            calculateMaintenanceCost()
        );
    }


    @Override
    public String toString() {
        return String.format(
            "TransportUnit{resourceID=%s, location=%s, status=%s, fuelType=%s, capacity=%d, currentPassengers=%d}",
            getResourceID(),
            getLocation(),
            getStatus(),
            fuelType,
            capacity,
            currentPassengers
        );
    }

    // to cnverth the each of the object data in the row format so that it can be easily inserted into  the table and displayed in the jTable
    public Object[] toTableRow(){
        return new Object [] {resourceID, location, status , fuelType, capacity};
    }
}
