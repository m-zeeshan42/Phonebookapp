import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditPanel extends JPanel {
    private JComboBox<String> contactList;
    private JTextField newNameField;
    private JTextField newPhoneField;
    private Contact selectedContact; // Store the selected contact

    public EditPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE); // Set background color to white

        // Title label
        JLabel titleLabel = new JLabel("Edit Contact", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Initialize components
        updateContactList();

        // Create fields for new name and phone number
        newNameField = new JTextField(20);
        newPhoneField = new JTextField(20);

        // Update button
        JButton updateButton = new JButton("Update Contact");
        updateButton.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Button font
        updateButton.setFocusPainted(false); // Remove focus border
        updateButton.setBackground(Color.LIGHT_GRAY); // Set background color to light gray
        updateButton.setForeground(Color.BLACK); // Set text color to black

        // Create a rounded border
        int borderRadius = 20; // Adjust the radius as needed
        updateButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY), // Outer line border
                BorderFactory.createEmptyBorder(10, borderRadius, 10, borderRadius) // Inner empty border for padding
        ));

        updateButton.addActionListener(e -> {
            if (selectedContact != null) { // Ensure selectedContact is not null
                String newName = newNameField.getText();
                String newPhone = newPhoneField.getText();
                Phonebook.getInstance().updateContact(selectedContact.getName(), newName, newPhone);
                JOptionPane.showMessageDialog(null, "Contact updated successfully!");

                // Clear fields after updating contact
                newNameField.setText("");
                newPhoneField.setText("");

                // Update display in homePanel after updating contact
                PhonebookGUI gui = (PhonebookGUI) SwingUtilities.getWindowAncestor(EditPanel.this);
                HomePanel homePanel = (HomePanel) gui.getPanel("home");
                homePanel.updateDisplay();

                // Switch to home panel
                gui.showPanel("home");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a contact first.");
            }
        });

        // Optional: Adjust padding and margins for better appearance
        updateButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Delete button
        JButton deleteButton = new JButton("Delete Contact");
        deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Button font
        deleteButton.setFocusPainted(false); // Remove focus border
        deleteButton.setBackground(new Color(255, 128, 128)); // Light red background color
        deleteButton.setForeground(Color.RED); // Red text color

        // Create a rounded border

        deleteButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.RED), // Outer line border
                BorderFactory.createEmptyBorder(10, borderRadius, 10, borderRadius) // Inner empty border for padding
        ));

        deleteButton.addActionListener(e -> {
            if (selectedContact != null) { // Ensure selectedContact is not null
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this contact?",
                        "Delete Contact", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    Phonebook.getInstance().deleteContact(selectedContact.getName());
                    JOptionPane.showMessageDialog(null, "Contact deleted successfully!");

                    // Clear fields after deleting contact
                    newNameField.setText("");
                    newPhoneField.setText("");

                    // Update display in homePanel after deleting contact
                    PhonebookGUI gui = (PhonebookGUI) SwingUtilities.getWindowAncestor(EditPanel.this);
                    HomePanel homePanel = (HomePanel) gui.getPanel("home");
                    homePanel.updateDisplay();

                    // Switch to home panel
                    gui.showPanel("home");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a contact first.");
            }
        });

        // Optional: Adjust padding and margins for better appearance
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Form panel with fields
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(newNameField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(newPhoneField);
        formPanel.add(new JLabel()); // Placeholder
        formPanel.add(updateButton);
        formPanel.add(new JLabel()); // Placeholder
        formPanel.add(deleteButton); // Add delete button
        formPanel.setBackground(Color.WHITE); // Match background color
        add(formPanel, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBorderPainted(false); // Remove button border
        backButton.setFocusPainted(false); // Remove focus border
        backButton.setContentAreaFilled(false); // Remove default background

        // ActionListener remains the same
        backButton.addActionListener(e -> {
            PhonebookGUI gui = (PhonebookGUI) SwingUtilities.getWindowAncestor(EditPanel.this);
            gui.showPanel("home");
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE); // Match background color
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void updateContactList() {
        contactList = new JComboBox<>();
        for (Contact contact : Phonebook.getInstance().getContacts()) {
            contactList.addItem(contact.getName());
        }
        contactList.addActionListener(e -> {
            // Update name and phone fields when a different contact is selected
            String selectedName = (String) contactList.getSelectedItem();
            selectedContact = Phonebook.getInstance().getContact(selectedName); // Update selected contact
            if (selectedContact != null) {
                newNameField.setText(selectedContact.getName()); // Initialize new name with old value
                newPhoneField.setText(selectedContact.getPhone()); // Initialize new phone with old value
            }
        });
    }

    // Method to populate fields with a specific contact
    public void populateFields(Contact contact) {
        selectedContact = contact; // Set the selected contact
        newNameField.setText(contact.getName()); // Initialize new name with old value
        newPhoneField.setText(contact.getPhone()); // Initialize new phone with old value
    }
}
