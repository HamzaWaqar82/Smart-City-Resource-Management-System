package Main;

public abstract class CityResource implements Reportable{
    protected String resourceID;
    protected String location;
    protected String status;
    protected int x, y; //coordinaties for map

    public CityResource(String resourceID, String location, String status, int x, int y){
        this.resourceID = resourceID;
        this.location = location;
        this.status= status;
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
