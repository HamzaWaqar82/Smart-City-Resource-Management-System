package Interface;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Main.Consumer;
import Main.EmergencyService;
import Main.PowerStation;
import Main.SmartGrid;

public class PnlSmartGrid {
    private static List<SmartGrid> gridList = new ArrayList<>();
    private static DefaultTableModel gridTableModel;
    private static DefaultTableModel gridPowerStationTableModel;
    private static DefaultTableModel gridConsumerTableModel;
    private static PnlMap pnlMap;

    public static JPanel createSmartGridPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Grid Management
        JLabel lblGridID = new JLabel("Grid Name:");
        lblGridID.setBounds(20, 20, 100, 25);
        panel.add(lblGridID);
        JTextField txtGridID = new JTextField();
        txtGridID.setBounds(130, 20, 150, 25);
        panel.add(txtGridID);

        JButton btnAddGrid = new JButton("Add Grid");
        btnAddGrid.setBounds(20, 60, 120, 30);
        panel.add(btnAddGrid);
        JButton btnDeleteGrid = new JButton("Delete Grid");
        btnDeleteGrid.setBounds(160, 60, 120, 30);
        panel.add(btnDeleteGrid);

        // Grid Table
        String[] gridColumns = { "Grid Name", "# PowerStations", "# Consumers", "Total Generated (MW)",
                "Total Consumed (MW)", "Balance (MW)" };
        gridTableModel = new DefaultTableModel(gridColumns, 0);
        JTable gridTable = new JTable(gridTableModel);
        JScrollPane gridScrollPane = new JScrollPane(gridTable);
        gridScrollPane.setBounds(320, 20, 600, 120);
        panel.add(gridScrollPane);

        // PowerStation Management in Grid
        JLabel lblPS = new JLabel("Power Stations in Grid:");
        lblPS.setBounds(20, 110, 200, 25);
        panel.add(lblPS);
        String[] psColumns = { "ID", "Location", "Status", "Energy Output", "Type" };
        gridPowerStationTableModel = new DefaultTableModel(psColumns, 0);
        JTable gridPSTable = new JTable(gridPowerStationTableModel);
        JScrollPane psScrollPane = new JScrollPane(gridPSTable);
        psScrollPane.setBounds(320, 150, 600, 100);
        panel.add(psScrollPane);

        JButton btnAddPS = new JButton("Add Existing PowerStation");
        btnAddPS.setBounds(20, 140, 260, 30);
        panel.add(btnAddPS);
        JButton btnRemovePS = new JButton("Remove PowerStation");
        btnRemovePS.setBounds(20, 180, 260, 30);
        panel.add(btnRemovePS);

        // Consumer Management in Grid
        JLabel lblConsumer = new JLabel("Consumers in Grid:");
        lblConsumer.setBounds(20, 230, 200, 25);
        panel.add(lblConsumer);
        String[] consumerColumns = { "ID", "Type", "Energy Consumption (MW)" };
        gridConsumerTableModel = new DefaultTableModel(consumerColumns, 0);
        JTable gridConsumerTable = new JTable(gridConsumerTableModel);
        JScrollPane consumerScrollPane = new JScrollPane(gridConsumerTable);
        consumerScrollPane.setBounds(320, 270, 600, 100);
        panel.add(consumerScrollPane);

        JButton btnAddConsumer = new JButton("Add Consumer");
        btnAddConsumer.setBounds(20, 260, 120, 30);
        panel.add(btnAddConsumer);
        JButton btnRemoveConsumer = new JButton("Remove Consumer");
        btnRemoveConsumer.setBounds(160, 260, 120, 30);
        panel.add(btnRemoveConsumer);

        // Energy Balance and Alert
        JButton btnCheckBalance = new JButton("Check Energy Balance & Alert");
        btnCheckBalance.setBounds(20, 320, 260, 30);
        panel.add(btnCheckBalance);

        // Map Panel
        pnlMap = new PnlMap(new ArrayList<>());
        pnlMap.setBounds(20, 370, 900, 200);
        panel.add(pnlMap);

        // Populate PowerStation selection dialog from PnlPowerStation.powerStationList
        btnAddPS.addActionListener(e -> {
            int gridRow = gridTable.getSelectedRow();
            if (gridRow == -1) {
                JOptionPane.showMessageDialog(panel, "Select a grid first.");
                return;
            }
            SmartGrid grid = gridList.get(gridRow);
            // Show dialog to select from available power stations
            List<PowerStation> available = new ArrayList<>();
            for (PowerStation ps : PnlPowerStation.powerStationList) {
                if (!grid.getPowerStations().contains(ps)) {
                    available.add(ps);
                }
            }
            if (available.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "No available power stations to add.");
                return;
            }
            String[] psNames = available.stream().map(PowerStation::getResourceID).toArray(String[]::new);
            String selected = (String) JOptionPane.showInputDialog(panel, "Select PowerStation:", "Add PowerStation",
                    JOptionPane.PLAIN_MESSAGE, null, psNames, psNames[0]);
            if (selected != null) {
                for (PowerStation ps : available) {
                    if (ps.getResourceID().equals(selected)) {
                        grid.addPowerStation(ps);
                        break;
                    }
                }
                updateGridTable();
                updateGridPowerStationTable(grid);
                pnlMap.updateMap(new ArrayList<>(grid.getPowerStations()));
            }
        });

        btnRemovePS.addActionListener(e -> {
            int gridRow = gridTable.getSelectedRow();
            int psRow = gridPSTable.getSelectedRow();
            if (gridRow == -1 || psRow == -1) {
                JOptionPane.showMessageDialog(panel, "Select a grid and a power station in the grid.");
                return;
            }
            SmartGrid grid = gridList.get(gridRow);
            if (psRow < grid.getPowerStations().size()) {
                grid.getPowerStations().remove(psRow);
                updateGridTable();
                updateGridPowerStationTable(grid);
                pnlMap.updateMap(new ArrayList<>(grid.getPowerStations()));
            }
        });

        btnAddGrid.addActionListener(e -> {
            String name = txtGridID.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Enter a grid name.");
                return;
            }
            SmartGrid grid = new SmartGrid();
            gridList.add(grid);
            updateGridTable();
            txtGridID.setText("");
        });

        btnDeleteGrid.addActionListener(e -> {
            int row = gridTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(panel, "Select a grid to delete.");
                return;
            }
            gridList.remove(row);
            updateGridTable();
            gridPowerStationTableModel.setRowCount(0);
            gridConsumerTableModel.setRowCount(0);
            pnlMap.updateMap(new ArrayList<>());
        });

        gridTable.getSelectionModel().addListSelectionListener(e -> {
            int row = gridTable.getSelectedRow();
            if (row != -1 && row < gridList.size()) {
                SmartGrid grid = gridList.get(row);
                updateGridPowerStationTable(grid);
                updateGridConsumerTable(grid);
                pnlMap.updateMap(new ArrayList<>(grid.getPowerStations()));
            }
        });

        btnAddConsumer.addActionListener(e -> {
            int gridRow = gridTable.getSelectedRow();
            if (gridRow == -1) {
                JOptionPane.showMessageDialog(panel, "Select a grid first.");
                return;
            }
            String id = JOptionPane.showInputDialog(panel, "Enter Consumer ID:");
            String type = JOptionPane.showInputDialog(panel, "Enter Consumer Type:");
            String consumptionStr = JOptionPane.showInputDialog(panel, "Enter Energy Consumption (MW):");
            try {
                double consumption = Double.parseDouble(consumptionStr);
                Consumer consumer = new Consumer(id, type, consumption);
                gridList.get(gridRow).addConsumer(consumer);
                updateGridConsumerTable(gridList.get(gridRow));
                updateGridTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input for energy consumption.");
            }
        });

        btnRemoveConsumer.addActionListener(e -> {
            int gridRow = gridTable.getSelectedRow();
            int consumerRow = gridConsumerTable.getSelectedRow();
            if (gridRow == -1 || consumerRow == -1) {
                JOptionPane.showMessageDialog(panel, "Select a grid and a consumer.");
                return;
            }
            SmartGrid grid = gridList.get(gridRow);
            if (consumerRow < grid.getConsumers().size()) {
                grid.getConsumers().remove(consumerRow);
                updateGridConsumerTable(grid);
                updateGridTable();
            }
        });

        btnCheckBalance.addActionListener(e -> {
            int gridRow = gridTable.getSelectedRow();
            if (gridRow == -1) {
                JOptionPane.showMessageDialog(panel, "Select a grid first.");
                return;
            }
            SmartGrid grid = gridList.get(gridRow);
            double generated = grid.getTotalEnergyGenerated();
            double consumed = grid.getTotalEnergyConsumed();
            double balance = generated - consumed;
            if (balance < 0) {
                // Send alert to first available emergency service
                EmergencyService es = null;
                if (!PnlEmergeny.emergencyList.isEmpty()) {
                    es = PnlEmergeny.emergencyList.get(0);
                    es.sendEmergencyAlert();
                }
                JOptionPane.showMessageDialog(panel, "ALERT: Consumption exceeds generation by " + Math.abs(balance)
                        + " MW.\nEmergency service notified." + (es != null ? (" (" + es.getLocation() + ")") : ""),
                        "Energy Alert", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel,
                        "Energy is balanced.\nGenerated: " + generated + " MW\nConsumed: " + consumed + " MW",
                        "Energy OK", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Initial table update
        updateGridTable();

        return panel;
    }

    private static void updateGridTable() {
        gridTableModel.setRowCount(0);
        for (int i = 0; i < gridList.size(); i++) {
            SmartGrid grid = gridList.get(i);
            double generated = grid.getTotalEnergyGenerated();
            double consumed = grid.getTotalEnergyConsumed();
            double balance = generated - consumed;
            gridTableModel.addRow(new Object[] {
                    "Grid " + (i + 1),
                    grid.getPowerStations().size(),
                    grid.getConsumers().size(),
                    String.format("%.2f", generated),
                    String.format("%.2f", consumed),
                    String.format("%.2f", balance)
            });
        }
    }

    private static void updateGridPowerStationTable(SmartGrid grid) {
        gridPowerStationTableModel.setRowCount(0);
        for (PowerStation ps : grid.getPowerStations()) {
            gridPowerStationTableModel.addRow(new Object[] {
                    ps.getResourceID(),
                    ps.getLocation(),
                    ps.getStatus(),
                    ps.getEnergyOutput(),
                    ps.getStationType()
            });
        }
    }

    private static void updateGridConsumerTable(SmartGrid grid) {
        gridConsumerTableModel.setRowCount(0);
        for (Consumer c : grid.getConsumers()) {
            gridConsumerTableModel.addRow(new Object[] {
                    c.getId(),
                    c.getType(),
                    c.getEnergyConsumption()
            });
        }
    }
}
