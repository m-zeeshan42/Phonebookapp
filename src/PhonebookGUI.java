import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PhonebookGUI extends JFrame {
    private CardLayout cardLayout;

    private JPanel cardPanel;
    private Map<String, JPanel> panels;

    public PhonebookGUI() {
        setTitle("Phonebook Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        panels = new HashMap<>();

        HomePanel homePanel = new HomePanel();
        panels.put("home", homePanel);
        cardPanel.add(homePanel, "home");

        AddPanel addPanel = new AddPanel();
        panels.put("add", addPanel);
        cardPanel.add(addPanel, "add");

        EditPanel editPanel = new EditPanel();
        panels.put("edit", editPanel);
        cardPanel.add(editPanel, "edit");

        add(cardPanel);
        showPanel("home");
    }

    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);
    }

    public JPanel getPanel(String name) {
        return panels.get(name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhonebookGUI frame = new PhonebookGUI();
            frame.setVisible(true);
        });
    }
}
