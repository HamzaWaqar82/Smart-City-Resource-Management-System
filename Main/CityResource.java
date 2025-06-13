package Main;

public abstract class CityResource implements Reportable{
    protected String resourceID;
    protected String location;
    protected String status;


    public CityResource(String resourceID, String location, String status){
        this.resourceID = resourceID;
        this.location = location;
        this.status= status;
    }

// Getters
    public String getResourceID() {
        return resourceID;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }


    // maintainenece method
    public abstract double calculateMaintenanceCost();

    @Override
    public String toString() {
        return "Resource ID: " + resourceID +
               ", Location: " + location +
               ", Status: " + status;
    }

    
}
