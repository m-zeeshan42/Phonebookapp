import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Phonebook {
    private List<Contact> contacts;

    // Singleton instance
    private static Phonebook instance = new Phonebook();

    // Private constructor to prevent instantiation
    private Phonebook() {
        contacts = new ArrayList<>();
        loadContactsFromFile(); // Load contacts from file on initialization
    }

    // Static method to get the singleton instance
    public static Phonebook getInstance() {
        return instance;
    }

    // Method to set contacts (replace existing contacts with new list)
    public void setContacts(List<Contact> newContacts) {
        contacts = newContacts;
        saveContactsToFile(); // Save contacts to file after setting
    }

    // Method to get all contacts
    public List<Contact> getContacts() {
        return contacts;
    }

    // Method to get a contact by name
    public Contact getContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(name)) {
                return contact;
            }
        }
        return null; // Return null if contact with given name is not found
    }

    // Method to add a contact
    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContactsToFile(); // Save contacts to file after adding
    }

    // Method to update a contact
    public void updateContact(String oldName, String newName, String newPhone) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(oldName)) {
                contact.setName(newName);
                contact.setPhone(newPhone);
                saveContactsToFile(); // Save contacts to file after updating
                break;
            }
        }
    }

    // Method to delete a contact by name
    public void deleteContact(String name) {
        contacts.removeIf(contact -> contact.getName().equals(name));
        saveContactsToFile(); // Save contacts to file after deleting
    }

    // Method to save contacts to a file
    private void saveContactsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts.dat"))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load contacts from a file
    @SuppressWarnings("unchecked")
    private void loadContactsFromFile() {
        File file = new File("contacts.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                contacts = (List<Contact>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
