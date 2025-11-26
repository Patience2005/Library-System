import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfessionalLibraryGUI extends JFrame {
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
    
    // Professional color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);

    public ProfessionalLibraryGUI() {
        this.libraryRepo = new LibraryRepository();
        this.circulationService = new CirculationService(libraryRepo);
        this.catalogService = new CatalogService(libraryRepo);
        this.authService = new AuthService();
        
        initializeProfessionalGUI();
    }

    private void initializeProfessionalGUI() {
        setTitle("Library Management System - Professional Edition");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set professional look
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        JTabbedPane tabbedPane = createProfessionalTabbedPane();
        
        // Search Tab
        JPanel searchPanel = createProfessionalSearchPanel();
        tabbedPane.addTab("Search Books", searchPanel);
        
        // Add Book Tab
        JPanel addBookPanel = createProfessionalAddBookPanel();
        tabbedPane.addTab("Add Book", addBookPanel);
        
        // Statistics Tab
        JPanel statsPanel = createProfessionalStatsPanel();
        tabbedPane.addTab("Statistics", statsPanel);
        
        add(tabbedPane);
        
        // Load initial books
        refreshBookList();
    }

    private JTabbedPane createProfessionalTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(TEXT_COLOR);
        return tabbedPane;
    }

    private JPanel createProfessionalSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Professional search header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        
        JLabel titleLabel = new JLabel("Search Library Books");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Search controls
        JPanel searchControls = new JPanel(new FlowLayout());
        searchControls.setBackground(BACKGROUND_COLOR);
        searchControls.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        searchField = createProfessionalTextField(25, "Enter book title, author, or ISBN...");
        JButton searchButton = createProfessionalButton("Search", SUCCESS_COLOR);
        JButton clearButton = createProfessionalButton("Clear", Color.GRAY);
        
        searchControls.add(searchLabel);
        searchControls.add(searchField);
        searchControls.add(searchButton);
        searchControls.add(clearButton);
        
        // Output area
        outputArea = createProfessionalTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(Color.WHITE);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(searchControls, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        searchButton.addActionListener(e -> performSearch());
        clearButton.addActionListener(e -> clearSearch());
        
        // Add enter key support
        searchField.addActionListener(e -> performSearch());
        
        return panel;
    }

    private JPanel createProfessionalAddBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        
        JLabel titleLabel = new JLabel("Add New Book");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Create form fields
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createProfessionalLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        isbnField = createProfessionalTextField(20, "Enter ISBN (e.g., 978-0-13-468599-1)");
        formPanel.add(isbnField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(createProfessionalLabel("Title:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        titleField = createProfessionalTextField(20, "Enter book title");
        formPanel.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(createProfessionalLabel("Author:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        authorField = createProfessionalTextField(20, "Enter author name");
        formPanel.add(authorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(createProfessionalLabel("Type:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        String[] types = {"Fiction", "NonFiction", "Reference"};
        typeComboBox = new JComboBox<>(types);
        typeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeComboBox.setBackground(Color.WHITE);
        formPanel.add(typeComboBox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(createProfessionalLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        categoryField = createProfessionalTextField(20, "Enter category (e.g., Programming, Fantasy)");
        formPanel.add(categoryField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton addButton = createProfessionalButton("Add Book", SUCCESS_COLOR);
        JButton resetButton = createProfessionalButton("Reset Form", Color.GRAY);
        
        buttonPanel.add(addButton);
        buttonPanel.add(resetButton);
        formPanel.add(buttonPanel, gbc);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        
        addButton.addActionListener(e -> addBook());
        resetButton.addActionListener(e -> resetForm());
        
        return panel;
    }

    private JPanel createProfessionalStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(155, 89, 182));
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        
        JLabel titleLabel = new JLabel("Library Statistics");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Stats content
        JTextArea statsArea = createProfessionalTextArea();
        statsArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        
        // Calculate statistics
        List<LibraryItem> allBooks = libraryRepo.getAllBooks();
        int totalBooks = allBooks.size();
        int fictionCount = 0;
        int nonFictionCount = 0;
        int referenceCount = 0;
        
        for (LibraryItem book : allBooks) {
            if (book instanceof FictionBook) fictionCount++;
            else if (book instanceof NonFictionBook) nonFictionCount++;
            else if (book instanceof ReferenceBook) referenceCount++;
        }
        
        StringBuilder stats = new StringBuilder();
        stats.append("LIBRARY STATISTICS\n");
        stats.append("========================\n\n");
        stats.append("Total Books: ").append(totalBooks).append("\n");
        stats.append("Fiction Books: ").append(fictionCount).append("\n");
        stats.append("Non-Fiction Books: ").append(nonFictionCount).append("\n");
        stats.append("Reference Books: ").append(referenceCount).append("\n\n");
        stats.append("COLLECTION BREAKDOWN:\n");
        stats.append("========================\n");
        stats.append("Fiction: ").append(String.format("%.1f%%", (fictionCount * 100.0 / totalBooks))).append("\n");
        stats.append("Non-Fiction: ").append(String.format("%.1f%%", (nonFictionCount * 100.0 / totalBooks))).append("\n");
        stats.append("Reference: ").append(String.format("%.1f%%", (referenceCount * 100.0 / totalBooks))).append("\n\n");
        stats.append("SYSTEM STATUS: OPERATIONAL\n");
        stats.append("Last Updated: ").append(new java.util.Date()).append("\n");
        
        statsArea.setText(stats.toString());
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(statsArea), BorderLayout.CENTER);
        
        return panel;
    }

    private JTextField createProfessionalTextField(int columns, String placeholder) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(TEXT_COLOR);
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(TEXT_COLOR);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        
        return textField;
    }

    private JTextArea createProfessionalTextArea() {
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(TEXT_COLOR);
        textArea.setEditable(false);
        return textArea;
    }

    private JButton createProfessionalButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private JLabel createProfessionalLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty() || searchTerm.equals("Enter book title, author, or ISBN...")) {
            refreshBookList();
            return;
        }
        
        List<LibraryItem> results = catalogService.searchBooks(searchTerm);
        
        StringBuilder output = new StringBuilder();
        output.append("SEARCH RESULTS FOR '").append(searchTerm.toUpperCase()).append("'\n");
        output.append("=====================================================\n\n");
        
        if (results.isEmpty()) {
            output.append("No books found matching '").append(searchTerm).append("'\n");
            output.append("Try searching with different keywords\n");
        } else {
            output.append("Found ").append(results.size()).append(" book(s):\n\n");
            int count = 1;
            for (LibraryItem book : results) {
                output.append(String.format("%d. %s\n", count++, book.toString()));
            }
        }
        
        outputArea.setText(output.toString());
        outputArea.setCaretPosition(0);
    }

    private void clearSearch() {
        searchField.setText("Enter book title, author, or ISBN...");
        searchField.setForeground(Color.GRAY);
        refreshBookList();
    }

    private void addBook() {
        String isbn = isbnField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        String category = categoryField.getText().trim();
        
        // Validation
        if (isbn.isEmpty() || isbn.equals("Enter ISBN (e.g., 978-0-13-468599-1)") ||
            title.isEmpty() || title.equals("Enter book title") ||
            author.isEmpty() || author.equals("Enter author name") ||
            category.isEmpty() || category.equals("Enter category (e.g., Programming, Fantasy)")) {
            
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields with valid information!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(this, 
                    "Invalid book type selected!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
        }
        
        libraryRepo.addBook(book);
        
        JOptionPane.showMessageDialog(this, 
            "Book added successfully!\n\n" +
            "Title: " + title + "\n" +
            "Author: " + author + "\n" +
            "ISBN: " + isbn + "\n" +
            "Type: " + type + "\n" +
            "Category: " + category, 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
        
        resetForm();
        refreshBookList();
    }

    private void resetForm() {
        isbnField.setText("Enter ISBN (e.g., 978-0-13-468599-1)");
        isbnField.setForeground(Color.GRAY);
        titleField.setText("Enter book title");
        titleField.setForeground(Color.GRAY);
        authorField.setText("Enter author name");
        authorField.setForeground(Color.GRAY);
        categoryField.setText("Enter category (e.g., Programming, Fantasy)");
        categoryField.setForeground(Color.GRAY);
        typeComboBox.setSelectedIndex(0);
    }

    private void refreshBookList() {
        List<LibraryItem> allBooks = libraryRepo.getAllBooks();
        
        StringBuilder output = new StringBuilder();
        output.append("COMPLETE LIBRARY COLLECTION\n");
        output.append("=====================================================\n\n");
        output.append("Total Books: ").append(allBooks.size()).append("\n\n");
        
        int count = 1;
        for (LibraryItem book : allBooks) {
            output.append(String.format("%3d. %s\n", count++, book.toString()));
        }
        
        outputArea.setText(output.toString());
        outputArea.setCaretPosition(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProfessionalLibraryGUI gui = new ProfessionalLibraryGUI();
            gui.setVisible(true);
        });
    }
}
