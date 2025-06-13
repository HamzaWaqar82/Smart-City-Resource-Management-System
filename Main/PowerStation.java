package Main;

public class PowerStation extends CityResource implements Alertable { // no need to impliment the Reportable here because its already impliment in the base class city resource
    private double energyOutput;
    private boolean outageProne;
    private String stationType;

    public PowerStation( String resourceID, String location, String status, double energyOutput, boolean outageProne, String stationType){
        super(resourceID, location, status);

        this.energyOutput = energyOutput;
        this.outageProne = outageProne;
        this.stationType = stationType;
    }


    @Override
    public double calculateMaintenanceCost() {
        // Example calculation: base cost + variable cost based on energy output and outage risk
        double baseCost = 1000.0;
        double outputCost = energyOutput * 0.05;
        double outageCost = outageProne ? 500.0 : 0.0;
        return baseCost + outputCost + outageCost;
    }

    @Override
    public String generateUserReport() {
        return "PowerStation Report:\n" +
               "Resource ID: " + getResourceID() + "\n" +
               "Location: " + getLocation() + "\n" +
               "Status: " + getStatus() + "\n" +
               "Energy Output: " + energyOutput + " MW\n" +
               "Outage Prone: " + (outageProne ? "Yes" : "No") + "\n" +
               "Station Type: " + stationType + "\n" +
               "Maintenance Cost: $" + calculateMaintenanceCost();
    }

    @Override
    public void sendEmergencyAlert() {
        System.out.println("EMERGENCY ALERT: PowerStation " + getResourceID() + " at " + getLocation() +
                " is experiencing an emergency! Status: " + getStatus());
    }

    @Override
    public String toString() {
        return "PowerStation{" +
                "resourceID='" + getResourceID() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", energyOutput=" + energyOutput +
                ", outageProne=" + outageProne +
                ", stationType='" + stationType + '\'' +
                '}';
    }
}
