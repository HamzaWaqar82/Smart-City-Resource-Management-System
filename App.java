import javax.swing.SwingUtilities;

import Main.UserInterface;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserInterface().renderDashboard();
        });
    }
}
