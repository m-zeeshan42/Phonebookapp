import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomePanel extends JPanel {
    private JPanel cardContainer;
    private JTextField searchField;

    public HomePanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE); // Set background color to white

        // Title panel with search and sort
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Bottom margin of 20
        titlePanel.setBackground(Color.WHITE); // Match background color
        JLabel titleLabel = new JLabel("PhoneBook   ", JLabel.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Modern font and larger size
        titlePanel.add(titleLabel, BorderLayout.WEST);

        // Search field
        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Set font and size

        // Create a rounded border with padding
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY), // Outer line border
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Inner empty border for padding
        ));

        // Add document listener for real-time updates
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDisplay();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateDisplay();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not needed for plain text components
            }
        });

        // Add search field to titlePanel
        titlePanel.add(searchField, BorderLayout.CENTER);

        // Sort button
        JButton sortButton = new JButton("Sort Contacts");
        sortButton.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Button font
        sortButton.setFocusPainted(false); // Remove focus border
        sortButton.setBorderPainted(false); // Remove border
        sortButton.setContentAreaFilled(false);
        sortButton.addActionListener(e -> {
            sortContacts();
            updateDisplay();
        });
        titlePanel.add(sortButton, BorderLayout.EAST);

        add(titlePanel, BorderLayout.NORTH);

        cardContainer = new JPanel();
        cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.Y_AXIS));
        cardContainer.setBackground(Color.WHITE); // Match background color

        JScrollPane scrollPane = new JScrollPane(cardContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove border from scroll pane
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Increase scroll speed

        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton(new ImageIcon(getClass().getResource("plus.png")));
        addButton.setBorder(BorderFactory.createEmptyBorder());
        addButton.setContentAreaFilled(false);
        addButton.addActionListener(e -> {
            PhonebookGUI gui = (PhonebookGUI) SwingUtilities.getWindowAncestor(HomePanel.this);
            gui.showPanel("add");
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE); // Match background color
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateDisplay(); // Initial update of cardContainer
    }

    public void updateDisplay() {
        cardContainer.removeAll(); // Clear previous content

        List<Contact> contacts = Phonebook.getInstance().getContacts();
        String searchTerm = searchField.getText().trim().toLowerCase();

        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(searchTerm)) {
                JPanel card = createContactCard(contact);
                cardContainer.add(card);
            }
        }

        cardContainer.revalidate();
        cardContainer.repaint();
    }

    private void sortContacts() {
        List<Contact> contacts = Phonebook.getInstance().getContacts();
        Collections.sort(contacts, Comparator.comparing(Contact::getName));
        Phonebook.getInstance().setContacts(contacts); // Update sorted contacts in Phonebook
    }

    private JPanel createContactCard(Contact contact) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding inside card
        card.setBackground(Color.WHITE); // White background
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70)); // Increase height for better spacing
        card.setPreferredSize(new Dimension(400, 70)); // Fixed width

        // Icon
        JLabel iconLabel = createIconLabel("boy.png");
        if (iconLabel != null) {
            iconLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
            card.add(iconLabel, BorderLayout.WEST);
        }

        // Contact Info
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 5)); // Grid layout for name and phone
        infoPanel.setBackground(Color.WHITE); // Match background color
        JLabel nameLabel = new JLabel(contact.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Larger and bold font for name
        JLabel phoneLabel = new JLabel(contact.getPhone());
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Smaller font for phone
        infoPanel.add(nameLabel);
        infoPanel.add(phoneLabel);
        card.add(infoPanel, BorderLayout.CENTER);

        // Edit Button
        JButton editButton = new JButton("Edit");
        editButton.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Button font
        editButton.setFocusPainted(false); // Remove focus border
        editButton.setBorderPainted(false); // Remove border
        editButton.setContentAreaFilled(false);
        editButton.addActionListener(e -> {
            PhonebookGUI gui = (PhonebookGUI) SwingUtilities.getWindowAncestor(HomePanel.this);
            EditPanel editPanel = (EditPanel) gui.getPanel("edit");
            editPanel.populateFields(contact); // Pass the contact to EditPanel
            gui.showPanel("edit");
        });
        card.add(editButton, BorderLayout.EAST);

        return card;
    }

    private JLabel createIconLabel(String iconPath) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            return new JLabel(icon);
        } catch (Exception e) {
            System.err.println("Icon not found: " + iconPath);
            return null;
        }
    }
}
