package Main;

import java.util.ArrayList;
import java.util.List;

public class SmartGrid {
    private List<Consumer> consumers = new ArrayList<>();
    private List<PowerStation> powerStations = new ArrayList<>();

    public List<PowerStation> getPowerStations() {
        return powerStations;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void addPowerStation(PowerStation station) {
        powerStations.add(station);
    }

    public void addConsumer(Consumer consumer) {
        consumers.add(consumer);
    }

    public double getTotalEnergyGenerated() {
        double totalEnergy = 0;
        for (PowerStation station : powerStations) {
            totalEnergy += station.getEnergyOutput();
        }
        return totalEnergy;
    }

    public double getTotalEnergyConsumed() {
        double totalEnergy = 0;
        for (Consumer consumer : consumers) {
            totalEnergy += consumer.getEnergyConsumption();
        }
        return totalEnergy;
    }

    public void checkEnergyBalance() {
        double totalEnergyGenerated = getTotalEnergyGenerated();
        double totalEnergyConsumed = getTotalEnergyConsumed();
        double energyBalance = totalEnergyGenerated - totalEnergyConsumed;
        if (energyBalance < 0) {
            System.out.println("Energy consumption is greater than energy generated: " + energyBalance);
        }
    }
}
