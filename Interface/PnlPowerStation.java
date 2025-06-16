package Interface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.google.gson.reflect.TypeToken;

import Main.LocationManager;
import Main.PowerStation;

import java.awt.Point;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PnlPowerStation {
    protected static List<PowerStation> powerStationList = new ArrayList<>();
    private static DefaultTableModel powerStationTableModel;
    public static PnlMap pnlMap;

    protected static JPanel createPowerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // 1. label

        // 4. energy output
        // 5. outage
        // 6. type

        JLabel lblID = new JLabel("ID:");
        lblID.setBounds(20, 20, 100, 25);
        panel.add(lblID);

        JTextField txtID = new JTextField();
        txtID.setBounds(130, 20, 150, 25);
        panel.add(txtID);

        // 2.location
        JLabel lblLocation = new JLabel("Location:");
        lblLocation.setBounds(20, 60, 100, 25);
        panel.add(lblLocation);

        JTextField txtLocation = new JTextField();
        txtLocation.setBounds(130, 60, 150, 25);
        panel.add(txtLocation);

        // 3. status
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(20, 100, 100, 25);
        panel.add(lblStatus);

        JComboBox<String> comboBoxStatus = new JComboBox<>(new String[] { "Active", "Inactive", "Maintenance" });
        comboBoxStatus.setBounds(130, 100, 150, 25);
        panel.add(comboBoxStatus);

        // 4. energy outpur
        JLabel lblOutput = new JLabel("Energy Output (MW):");
        lblOutput.setBounds(20, 140, 120, 25);
        panel.add(lblOutput);

        JTextField txtOutput = new JTextField();
        txtOutput.setBounds(150, 140, 130, 25);
        panel.add(txtOutput);

        // 5. outage prone
        JLabel lblOutage = new JLabel("Outage Prone:");
        lblOutage.setBounds(20, 180, 100, 25);
        panel.add(lblOutage);

        JCheckBox chkOutage = new JCheckBox();
        chkOutage.setBounds(130, 180, 25, 25);
        panel.add(chkOutage);

        JLabel lblType = new JLabel("Station Type:");
        lblType.setBounds(20, 220, 100, 25);
        panel.add(lblType);

        JTextField txtType = new JTextField();
        txtType.setBounds(130, 220, 150, 25);
        panel.add(txtType);

        // Table
        String[] columns = { "ID", "Location", "Status", "Energy Output", "Outage Prone", "Station Type" };
        powerStationTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(powerStationTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(320, 20, 600, 300);
        panel.add(scrollPane);

        // Buttons
        JButton btnAdd = new JButton("Add Power Station");
        btnAdd.setBounds(20, 260, 260, 30);
        panel.add(btnAdd);

        JButton btnDelete = new JButton("Delete Power Station");
        btnDelete.setBounds(20, 300, 260, 30);
        panel.add(btnDelete);

        JButton btnCalcCost = new JButton("Calculate Maintenance Cost");
        btnCalcCost.setBounds(20, 340, 260, 30);
        panel.add(btnCalcCost);

        JButton btnReport = new JButton("Generate Usage Report");
        btnReport.setBounds(20, 380, 260, 30);
        panel.add(btnReport);

        JButton btnAlert = new JButton("Send Emergency Alert");
        btnAlert.setBounds(20, 420, 260, 30);
        panel.add(btnAlert);

        JButton btnSave = new JButton("Save to JSON");
        btnSave.setBounds(560, 340, 150, 30);
        panel.add(btnSave);

        JButton btnLoad = new JButton("Load from JSON");
        btnLoad.setBounds(720, 340, 150, 30);
        panel.add(btnLoad);


        pnlMap = new PnlMap(new ArrayList<>(powerStationList));
        pnlMap.setBounds(20, 460, 700, 300);
        panel.add(pnlMap);

        // Add PowerStation
        btnAdd.addActionListener(e -> {
            try {
                String id = txtID.getText();
                String location = txtLocation.getText();
                String status = (String) comboBoxStatus.getSelectedItem();
                double output = Double.parseDouble(txtOutput.getText());
                boolean outage = chkOutage.isSelected();
                String type = txtType.getText();

                Point locationCoordinate = LocationManager.assignLoaction();
                
                PowerStation ps = new PowerStation(id, location, status, locationCoordinate.x, locationCoordinate.y, output, outage, type);

                powerStationList.add(ps);
                powerStationTableModel.addRow(ps.toTableRow());
                pnlMap.updateMap(new ArrayList<>(powerStationList));

                txtID.setText("");
                txtLocation.setText("");
                comboBoxStatus.setSelectedIndex(0);
                txtOutput.setText("");
                chkOutage.setSelected(false);
                txtType.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input. Please check your entries.");
            }
        });

        // Delete PowerStation
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                powerStationList.remove(row);
                refreshPowerStationTableModel();
                pnlMap.updateMap(new ArrayList<>(powerStationList));
            } else {
                JOptionPane.showMessageDialog(panel, "Select a row first!");
            }
        });

        // Calculate Maintenance Cost
        btnCalcCost.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                PowerStation ps = powerStationList.get(row);
                double cost = ps.calculateMaintenanceCost();
                JOptionPane.showMessageDialog(panel, "Maintenance Cost: $" + cost);
            } else {
                JOptionPane.showMessageDialog(panel, "Select a Power Station first!");
            }
        });

        // Generate Usage Report
        btnReport.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                PowerStation ps = powerStationList.get(row);
                JOptionPane.showMessageDialog(panel, ps.generateUserReport());
            } else {
                JOptionPane.showMessageDialog(panel, "Select a Power Station first!");
            }
        });

        // Send Emergency Alert
        btnAlert.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                PowerStation ps = powerStationList.get(row);
                ps.sendEmergencyAlert();
                JOptionPane.showMessageDialog(panel, "Emergency alert sent (see console output).");
            } else {
                JOptionPane.showMessageDialog(panel, "Select a Power Station first!");
            }
        });

        // Save to JSON
        btnSave.addActionListener(e -> {
            Main.FileManager.save(panel, powerStationList, "power_stations.json", "json");
            JOptionPane.showMessageDialog(panel, "Power stations saved to JSON.");
        });

        // Load from JSON
        btnLoad.addActionListener(e -> {

            Type listType = new TypeToken<List<PowerStation>>() {
            }.getType();

            List<PowerStation> loaded = Main.FileManager.load(panel, listType, "power_stations.json", "json");

            if (loaded != null) {
                powerStationList.clear();
                powerStationTableModel.setRowCount(0);
                for (PowerStation ps : loaded) {
                    powerStationList.add(ps);
                    powerStationTableModel.addRow(ps.toTableRow());
                }
                JOptionPane.showMessageDialog(panel, "Power stations loaded from JSON.");
            } else {
                JOptionPane.showMessageDialog(panel, "Failed to load file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private static void refreshPowerStationTableModel() {
        powerStationTableModel.setRowCount(0);
        for (PowerStation ps : powerStationList) {
            powerStationTableModel.addRow(ps.toTableRow());
        }
    }
}
