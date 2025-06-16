package Main;

public class Consumer {
    private String id;
    private String type;
    private double energyConsumption;

    public Consumer(String id, String type, double energyConsumption) {
        this.id = id;
        this.type = type;
        this.energyConsumption = energyConsumption;

    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    @Override
    public String toString() {
        return "Consumer [id=" + id + ", type=" + type + ", energyConsumption=" + energyConsumption + "]";
    }
}
