package Main;
import javax.swing.*;

public class UserInterface extends JFrame{

    public void renderDashboard() {
        JFrame frame = new JFrame("Smart City Resource Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds( 10, 10, 960, 740);


        tabbedPane.add("Transport Unit", createTransportPanel());

        tabbedPane.add("Power Stations", createPowerPanel());

        tabbedPane.add("Emergency Services", createEmergencyPanel());


        frame.add(tabbedPane);


    }


    private JPanel createTransportPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);

        return panel;
    }

    private JPanel createPowerPanel(){
        return new JPanel();
    }

    private JPanel createEmergencyPanel(){
        return new JPanel();
    }
}