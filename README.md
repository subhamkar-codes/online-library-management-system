# 📚 Online Library Management System

A Java-based Library Management System with a modern dark-themed graphical user interface built using Java Swing.

This project started as a console-based Java application and was extended with a GUI to make library operations more interactive and user-friendly.

## ✨ Features

- **Dashboard:** View total, available, and issued book counts.
- **Add Books:** Add new books to the library.
- **View Books:** Browse available books and issued books.
- **Search Books:** Find books by title using the search field.
- **Issue Books:** Issue an available book.
- **Return Books:** Return an issued book.
- **Sort Books:** Arrange books alphabetically.
- **Dark UI:** Modern dashboard layout with a sidebar and summary cards.
- **Duplicate Prevention:** Prevents adding the same book title more than once, ignoring letter case.

## 🛠️ Technologies Used

- Java
- Java Swing
- Java AWT
- ArrayList
- Collections Framework
- JTable and DefaultTableModel

## 🚀 How to Run

### Prerequisites

- Java Development Kit (JDK)
- IntelliJ IDEA or another Java IDE

### Steps

1. Clone or download this repository.
2. Open the project in your Java IDE.
3. Ensure the GUI source file is named `OnlineLibraryGUI.java`.
4. Run the `main()` method in `OnlineLibraryGUI`.

Alternatively, compile and run from the terminal:

```bash
javac OnlineLibraryGUI.java
java OnlineLibraryGUI
```

## 📂 Project Structure

```text
OnlineLibrary/
├── OnlineLibrary.java       # Console version
├── OnlineLibraryGUI.java    # Java Swing GUI
└── README.md
```

## ⚠️ Current Limitations

- Book data is stored in memory using ArrayLists.
- Books are not saved permanently after the application closes.
- The console and GUI versions currently maintain separate book lists.
- The application does not use a database or user authentication.

## 🎯 Learning Goals

This project explores Java fundamentals, object-oriented programming concepts, collections, event-driven programming, and graphical user interface development.

## 🔮 Future Improvements

- Add permanent storage using file handling or a database.
- Connect the GUI to a shared library-management backend.
- Add book IDs, author information, and categories.
- Improve error handling and input validation.

## 👨‍💻 Author
 github.com/subhamkar-codes

Developed as a Java learning project to explore the transition from console applications to graphical user interfaces.
