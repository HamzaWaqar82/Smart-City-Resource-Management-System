package Main;

public class EmergencyService extends CityResource implements Alertable {
    private int responseTime;
    private String unitType;
    private int numberOFUnits;


    public EmergencyService(String resourceID, String location, String status, int x, int y, int responseTime, String unitType,
            int numberOFUnits) {
        super(resourceID, location, status, x, y);
        this.responseTime = responseTime;
        this.unitType = unitType;
        this.numberOFUnits = numberOFUnits;
    }

    @Override
    public double calculateMaintenanceCost() {
        // Example calculation: base cost per unit + cost per response time
        double baseCostPerUnit = 500.0;
        double responseTimeFactor = 50.0;
        return (numberOFUnits * baseCostPerUnit) + (responseTime * responseTimeFactor);
    }

    @Override
    public String generateUserReport() {
        return "Emergency Service Report:\n" +
                "Resource ID: " + getResourceID() + "\n" +
                "Location: " + getLocation() + "\n" +
                "Status: " + getStatus() + "\n" +
                "Response Time: " + responseTime + " mins\n" +
                "Unit Type: " + unitType + "\n" +
                "Number of Units: " + numberOFUnits + "\n" +
                "Maintenance Cost: " + calculateMaintenanceCost();
    }

    @Override
    public void sendEmergencyAlert() {
        System.out.println("Emergency Alert: " + unitType + " units dispatched to " + getLocation() +
                ". Response time: " + responseTime + " mins. Number of units: " + numberOFUnits + ".");
    }

    @Override
    public String toString() {
        return "EmergencyService{" +
                "resourceID='" + getResourceID() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", responseTime=" + responseTime +
                ", unitType='" + unitType + '\'' +
                ", numberOFUnits=" + numberOFUnits +
                '}';
    }

    public Object[] toTableRow() {
        return new Object[] { resourceID, location, status, responseTime, unitType, numberOFUnits };
    }
}
