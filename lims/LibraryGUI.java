import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LibraryGUI extends JFrame {
    private LibraryRepository libraryRepo;
    private CirculationService circulationService;
    private CatalogService catalogService;
    private AuthService authService;
    
    private JTextArea outputArea;
    private JTextField searchField;
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorField;
    private JComboBox<String> typeComboBox;
    private JTextField categoryField;

    public LibraryGUI() {
        this.libraryRepo = new LibraryRepository();
        this.circulationService = new CirculationService(libraryRepo);
        this.catalogService = new CatalogService(libraryRepo);
        this.authService = new AuthService();
        
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Library Management System - GUI Version");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Search Tab
        JPanel searchPanel = createSearchPanel();
        tabbedPane.addTab("Search Books", searchPanel);
        
        // Add Book Tab
        JPanel addBookPanel = createAddBookPanel();
        tabbedPane.addTab("Add Book", addBookPanel);
        
        // Borrow/Return Tab
        JPanel borrowPanel = createBorrowPanel();
        tabbedPane.addTab("Borrow/Return", borrowPanel);
        
        add(tabbedPane);
        
        // Load initial books
        refreshBookList();
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel searchControls = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        
        searchControls.add(new JLabel("Search:"));
        searchControls.add(searchField);
        searchControls.add(searchButton);
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        panel.add(searchControls, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        searchButton.addActionListener(e -> performSearch());
        
        return panel;
    }

    private JPanel createAddBookPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        isbnField = new JTextField(20);
        panel.add(isbnField, gbc);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(20);
        panel.add(titleField, gbc);
        
        // Author
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        authorField = new JTextField(20);
        panel.add(authorField, gbc);
        
        // Type
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        String[] types = {"Fiction", "NonFiction", "Reference"};
        typeComboBox = new JComboBox<>(types);
        panel.add(typeComboBox, gbc);
        
        // Category
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryField = new JTextField(20);
        panel.add(categoryField, gbc);
        
        // Add Button
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Book");
        panel.add(addButton, gbc);
        
        addButton.addActionListener(e -> addBook());
        
        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JTextArea borrowArea = new JTextArea("Borrow/Return functionality\n" +
            "Enter patron details and book ISBN to process transactions.\n" +
            "This integrates with your existing CirculationService.");
        borrowArea.setEditable(false);
        
        panel.add(new JScrollPane(borrowArea), BorderLayout.CENTER);
        
        return panel;
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            refreshBookList();
            return;
        }
        
        List<LibraryItem> results = catalogService.searchBooks(searchTerm);
        
        StringBuilder output = new StringBuilder();
        output.append("Search Results for '").append(searchTerm).append("':\n");
        output.append("=====================================\n");
        
        if (results.isEmpty()) {
            output.append("No books found.\n");
        } else {
            for (LibraryItem book : results) {
                output.append(book.toString()).append("\n");
            }
        }
        
        outputArea.setText(output.toString());
    }

    private void addBook() {
        String isbn = isbnField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        String category = categoryField.getText().trim();
        
        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LibraryItem book;
        switch (type.toLowerCase()) {
            case "fiction":
                book = new FictionBook(isbn, title, author, true, category);
                break;
            case "nonfiction":
                book = new NonFictionBook(isbn, title, author, true, category);
                break;
            case "reference":
                book = new ReferenceBook(isbn, title, author, true, category);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid book type!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }
        
        libraryRepo.addBook(book);
        JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // Clear fields
        isbnField.setText("");
        titleField.setText("");
        authorField.setText("");
        categoryField.setText("");
        
        refreshBookList();
    }

    private void refreshBookList() {
        List<LibraryItem> allBooks = libraryRepo.getAllBooks();
        
        StringBuilder output = new StringBuilder();
        output.append("All Books in Library:\n");
        output.append("=====================\n");
        
        for (LibraryItem book : allBooks) {
            output.append(book.toString()).append("\n");
        }
        
        outputArea.setText(output.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Simple launch without custom look and feel
            new LibraryGUI().setVisible(true);
        });
    }
}
