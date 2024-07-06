import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddPanel extends JPanel {
    public AddPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color to white
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title label
        JLabel titleLabel = new JLabel("Add Contact", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE); // Match background color

        JTextField nameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JButton addButton = new JButton("Add Contact");
        addButton.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Button font
        addButton.setFocusPainted(false); // Remove focus border
        addButton.setBackground(Color.LIGHT_GRAY); // Set background color to light gray
        addButton.setForeground(Color.BLACK); // Set text color to black

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            Phonebook.getInstance().addContact(new Contact(name, phone));
            JOptionPane.showMessageDialog(null, "Contact added successfully!");

            // Clear fields after adding contact
            nameField.setText("");
            phoneField.setText("");

            // Update display in homePanel after adding contact
            PhonebookGUI gui = (PhonebookGUI) SwingUtilities.getWindowAncestor(AddPanel.this);
            HomePanel homePanel = (HomePanel) gui.getPanel("home");
            homePanel.updateDisplay();

            // Switch to home panel
            gui.showPanel("home");
        });

        // Optional: Adjust padding and margins for better appearance
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(Color.WHITE); // Match background color
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel());
        formPanel.add(addButton);
        centerPanel.add(formPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Back button to go to homePanel
        JButton backButton = new JButton("Back");
        backButton.setFocusPainted(false); // Remove focus border
        backButton.setBorderPainted(false); // Remove border
        backButton.setContentAreaFilled(false); // Remove content area fill

        backButton.addActionListener(e -> {
            PhonebookGUI gui = (PhonebookGUI) SwingUtilities.getWindowAncestor(AddPanel.this);
            gui.showPanel("home");
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE); // Match background color
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }
}
