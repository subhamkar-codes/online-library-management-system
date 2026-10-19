
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OnlineLibraryGUI extends JFrame {

    private final Color BG = new Color(15, 23, 42);
    private final Color SIDEBAR = new Color(11, 18, 33);
    private final Color PANEL = new Color(24, 35, 56);
    private final Color ACCENT = new Color(45, 212, 191);
    private final Color TEXT = new Color(226, 232, 240);
    private final Color MUTED = new Color(148, 163, 184);

    private final List<String> availableBooks = new ArrayList<>();
    private final List<String> issuedBooks = new ArrayList<>();

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"#", "BOOK TITLE", "STATUS"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private JTable bookTable;
    private JTextField searchField;
    private JLabel pageTitle;
    private JLabel totalValue;
    private JLabel availableValue;
    private JLabel issuedValue;
    private JLabel statusLabel;

    private String currentView = "Dashboard";

    public OnlineLibraryGUI() {
        setTitle("Online Library Dashboard");
        setSize(1050, 680);
        setMinimumSize(new Dimension(850, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Start with an empty library. No sample books are added.

        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);

        refreshDashboard();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBackground(SIDEBAR);
        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  BOOKSHELF");
        logo.setFont(new Font("SansSerif", Font.BOLD, 21));
        logo.setForeground(ACCENT);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("  LIBRARY MANAGEMENT");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 10));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(35));

        addNavButton(sidebar, "Dashboard", "Dashboard");
        addNavButton(sidebar, "Available Books", "Available Books");
        addNavButton(sidebar, "Issued Books", "Issued Books");

        sidebar.add(Box.createVerticalStrut(20));

        JLabel actions = new JLabel("  BOOK ACTIONS");
        actions.setForeground(MUTED);
        actions.setFont(new Font("SansSerif", Font.BOLD, 11));
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(actions);
        sidebar.add(Box.createVerticalStrut(10));

        addNavButton(sidebar, "+  Add Book", "Add");
        addNavButton(sidebar, "↗  Issue Selected", "Issue");
        addNavButton(sidebar, "↙  Return Selected", "Return");

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel(
                "<html>ONLINE LIBRARY<br>Java Swing Edition</html>");
        footer.setForeground(MUTED);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(footer);

        return sidebar;
    }

    private void addNavButton(
            JPanel sidebar, String text, String action) {

        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(180, 42));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(0, 12, 0, 5));
        button.setBackground(SIDEBAR);
        button.setForeground(TEXT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 13));
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> handleNavigation(action));

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(5));
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(0, 20));
        main.setBackground(BG);
        main.setBorder(new EmptyBorder(25, 28, 25, 28));

        JPanel header = new JPanel(new BorderLayout(15, 12));
        header.setOpaque(false);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        pageTitle = new JLabel("Dashboard");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 27));
        pageTitle.setForeground(TEXT);

        JLabel description = new JLabel(
                "Manage your books in one place.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 13));
        description.setForeground(MUTED);

        titles.add(pageTitle);
        titles.add(Box.createVerticalStrut(5));
        titles.add(description);

        searchField = new JTextField(18);
        searchField.setPreferredSize(new Dimension(210, 38));
        searchField.setBackground(PANEL);
        searchField.setForeground(TEXT);
        searchField.setCaretColor(ACCENT);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(51, 65, 85)),
                new EmptyBorder(8, 10, 8, 10)));

        searchField.setToolTipText("Search book titles");

        header.add(titles, BorderLayout.WEST);
        header.add(searchField, BorderLayout.EAST);

        JPanel center = new JPanel(new BorderLayout(0, 18));
        center.setOpaque(false);

        JPanel stats = new JPanel(new GridLayout(1, 3, 14, 0));
        stats.setOpaque(false);

        totalValue = new JLabel("0");
        availableValue = new JLabel("0");
        issuedValue = new JLabel("0");

        stats.add(createStatCard(
                "TOTAL BOOKS", totalValue, ACCENT));
        stats.add(createStatCard(
                "AVAILABLE", availableValue,
                new Color(96, 165, 250)));
        stats.add(createStatCard(
                "ISSUED", issuedValue,
                new Color(251, 191, 36)));

        center.add(stats, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout(0, 12));
        tablePanel.setBackground(PANEL);
        tablePanel.setBorder(new EmptyBorder(18, 18, 15, 18));

        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setOpaque(false);

        JLabel tableTitle = new JLabel("BOOK COLLECTION");
        tableTitle.setForeground(TEXT);
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton sortButton = new JButton("Sort A-Z");
        styleButton(sortButton, false);
        sortButton.addActionListener(e -> sortBooks());

        tableHeader.add(tableTitle, BorderLayout.WEST);
        tableHeader.add(sortButton, BorderLayout.EAST);

        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(37);
        bookTable.setBackground(PANEL);
        bookTable.setForeground(TEXT);
        bookTable.setGridColor(new Color(51, 65, 85));
        bookTable.setSelectionBackground(new Color(41, 65, 83));
        bookTable.setSelectionForeground(Color.WHITE);
        bookTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        bookTable.setShowVerticalLines(false);
        bookTable.setFillsViewportHeight(true);
        bookTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        bookTable.getTableHeader().setBackground(
                new Color(30, 45, 67));
        bookTable.getTableHeader().setForeground(MUTED);
        bookTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 11));
        bookTable.getTableHeader().setPreferredSize(
                new Dimension(0, 35));
        bookTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(PANEL);

        statusLabel = new JLabel("Ready");
        statusLabel.setForeground(MUTED);
        statusLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 11));

        tablePanel.add(tableHeader, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(statusLabel, BorderLayout.SOUTH);

        center.add(tablePanel, BorderLayout.CENTER);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    @Override
                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        refreshTable();
                    }

                    @Override
                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        refreshTable();
                    }

                    @Override
                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        refreshTable();
                    }
                });

        return main;
    }

    private JPanel createStatCard(
            String title, JLabel value, Color accent) {

        JPanel card = new JPanel();
        card.setBackground(PANEL);
        card.setBorder(new EmptyBorder(17, 18, 17, 15));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel(title);
        heading.setForeground(MUTED);
        heading.setFont(
                new Font("SansSerif", Font.BOLD, 11));

        value.setForeground(accent);
        value.setFont(
                new Font("SansSerif", Font.BOLD, 30));

        card.add(heading);
        card.add(Box.createVerticalStrut(10));
        card.add(value);

        return card;
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(
                new Font("SansSerif", Font.BOLD, 12));
        button.setBorder(new EmptyBorder(9, 13, 9, 13));

        if (primary) {
            button.setBackground(ACCENT);
            button.setForeground(new Color(15, 23, 42));
        } else {
            button.setBackground(new Color(51, 65, 85));
            button.setForeground(TEXT);
        }
    }

    private void handleNavigation(String action) {
        switch (action) {
            case "Dashboard":
                currentView = "Dashboard";
                pageTitle.setText("Dashboard");
                refreshDashboard();
                break;

            case "Available Books":
                currentView = "Available Books";
                pageTitle.setText("Available Books");
                refreshDashboard();
                break;

            case "Issued Books":
                currentView = "Issued Books";
                pageTitle.setText("Issued Books");
                refreshDashboard();
                break;

            case "Add":
                addBook();
                break;

            case "Issue":
                currentView = "Available Books";
                pageTitle.setText("Available Books");
                refreshDashboard();
                statusLabel.setText(
                        "Select an available book, then click Issue Selected.");
                break;

            case "Return":
                currentView = "Issued Books";
                pageTitle.setText("Issued Books");
                refreshDashboard();
                statusLabel.setText(
                        "Select an issued book, then click Return Selected.");
                break;
        }
    }

    private void refreshDashboard() {
        int total = availableBooks.size() + issuedBooks.size();

        totalValue.setText(String.valueOf(total));
        availableValue.setText(
                String.valueOf(availableBooks.size()));
        issuedValue.setText(String.valueOf(issuedBooks.size()));

        refreshTable();
    }

    private void refreshTable() {
        if (tableModel == null || searchField == null) {
            return;
        }

        tableModel.setRowCount(0);

        String keyword = searchField.getText()
                .trim().toLowerCase();

        List<String> booksToShow = new ArrayList<>();

        if ("Available Books".equals(currentView)) {
            booksToShow.addAll(availableBooks);
        } else if ("Issued Books".equals(currentView)) {
            booksToShow.addAll(issuedBooks);
        } else {
            booksToShow.addAll(availableBooks);
            booksToShow.addAll(issuedBooks);
        }

        int number = 1;

        for (String book : booksToShow) {
            if (book.toLowerCase().contains(keyword)) {
                String status = issuedBooks.contains(book)
                        ? "Issued" : "Available";

                tableModel.addRow(new Object[]{
                        number++, book, status
                });
            }
        }

        statusLabel.setText(
                tableModel.getRowCount() + " book(s) displayed");
    }

    private void addBook() {
        String book = JOptionPane.showInputDialog(
                this, "Enter the book title:",
                "Add a Book", JOptionPane.PLAIN_MESSAGE);

        if (book == null) {
            return;
        }

        book = book.trim();

        if (book.isEmpty()) {
            showMessage("Book title cannot be empty.");
            return;
        }

        if (findBook(availableBooks, book) != null
                || findBook(issuedBooks, book) != null) {
            showMessage("This book already exists.");
            return;
        }

        availableBooks.add(book);
        currentView = "Available Books";
        pageTitle.setText("Available Books");
        searchField.setText("");
        refreshDashboard();
        showMessage("Book added successfully!");
    }

    private void issueSelectedBook() {
        if ("Issued Books".equals(currentView)) {
            showMessage(
                    "Open Available Books and select a book first.");
            return;
        }

        String book = getSelectedBook();

        if (book == null || !availableBooks.contains(book)) {
            showMessage(
                    "Select an available book from the table first.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this, "Issue \"" + book + "\"?",
                "Confirm Issue", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            availableBooks.remove(book);
            issuedBooks.add(book);
            refreshDashboard();
            showMessage("Book issued successfully!");
        }
    }

    private void returnSelectedBook() {
        if (!"Issued Books".equals(currentView)) {
            showMessage(
                    "Open Issued Books and select a book first.");
            return;
        }

        String book = getSelectedBook();

        if (book == null || !issuedBooks.contains(book)) {
            showMessage(
                    "Select an issued book from the table first.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this, "Return \"" + book + "\"?",
                "Confirm Return", JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            issuedBooks.remove(book);
            availableBooks.add(book);
            refreshDashboard();
            showMessage("Book returned successfully!");
        }
    }

    private String getSelectedBook() {
        int row = bookTable.getSelectedRow();

        if (row < 0) {
            return null;
        }

        Object value = tableModel.getValueAt(row, 1);
        return value == null ? null : value.toString();
    }

    private void sortBooks() {
        if ("Issued Books".equals(currentView)) {
            issuedBooks.sort(String.CASE_INSENSITIVE_ORDER);
        } else if ("Available Books".equals(currentView)) {
            availableBooks.sort(String.CASE_INSENSITIVE_ORDER);
        } else {
            availableBooks.sort(String.CASE_INSENSITIVE_ORDER);
            issuedBooks.sort(String.CASE_INSENSITIVE_ORDER);
        }

        refreshDashboard();
        statusLabel.setText("Books sorted alphabetically.");
    }

    private String findBook(List<String> books, String target) {
        for (String book : books) {
            if (book.equalsIgnoreCase(target)) {
                return book;
            }
        }
        return null;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Online Library",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use the default Swing look and feel.
            }

            OnlineLibraryGUI gui = new OnlineLibraryGUI();
            gui.setVisible(true);
        });
    }
}
