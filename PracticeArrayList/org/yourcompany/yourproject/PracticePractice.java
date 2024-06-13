package org.yourcompany.yourproject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// Create a Program that a user can create, remove, borrow and return a Book 
// in the library.
/**
 1. List all the possible selection that the program might have:
    Add Book
    Remove Book
    List all Books
    Get Book
    Return Book
    Exit
 2. If the Add Book is chosen, a user must be asked what the name, the author
    the content and the published year of the book, and it will be stored in
    the library. The ID should be added alongside with the book incrementally.
 3. If the Remove Book is chosen, it must list down all of the book with its
    metadata like ID, Name, Author and the Published Year only, 
    and then the user is asked what the ID of the Book to remove. If the valid 
    ID is given the book should be removed in the library.
 4. If the Get Book is chosen, it is same as the 3, but it must *temporarily*
    be removed in the library.
 5. If the Return Book is chosen, all Books that has been temporarily been
    removed from the library will list down all of its metadata (see No. 2) and
    the user will be asked for an ID of a Book to return on the library. If the
    valid ID is chosen, the book should be returned to the library.
 6. Otherwise if the Exit is chosen, the program must be terminated.
*/

public class PracticePractice {
    private ArrayList<Book> library = new ArrayList<>();
    private final ArrayList<Book> borrowers = new ArrayList<>();

    private final String separator = "========================================";
    private int index;


    private BufferedReader reader;

    public PracticePractice() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public boolean run() {
        System.out.println(separator);
        System.out.println("What do you want to do?");
        System.out.println("[A]dd Books");
        System.out.println("[R]emove Books");
        System.out.println("[L]ist all Books");
        System.out.println("[B]orrow Book");
        System.out.println("[P]ut Back the Book");
        System.out.println("[E]Exit");
        System.out.print(">>: ");

        char c = charInput();

        System.out.println(separator);
        switch (c) {
            case 'a':
                addBook();
                break;
            case 'r':
                removeBook();
                break;
            case 'l':
                listAllBooks(library);
                break;
            case 'b':
                borrowBook();
                break;
            case 'p':
                returnBook();
                break;
            case 'e':
                return false;
        }
        return true;
    }

    // The CRUD
    private void addBook() {
        System.out.print("Enter the book name >>: ");
        String name = stringInput();

        System.out.print("Enter the book author >>: ");
        String author = stringInput();

        System.out.print("Enter the published year of the book >>: ");
        int publishedYear = yearInput();

        Book book = new Book(index++, name, author, publishedYear);
        library.add(book);

        System.out.println("You have successfully added the book!");
    }

    private void removeBook() {
        if (library.isEmpty()) {
            System.out.println("There are no books in the library yet.");
            return;
        }
        listAllBooks(library);

        System.out.print("Enter the book ID to remove >>: ");

        while (true) {
            int id = numberInput();
            Book book = getBookById(id, library);
            if (book.getID() == -1) {
                System.out.print("This book does not exists. Try again >>: ");
                continue;
            }

            library.remove(book);
            System.out.println("You have successfully removed the book!");
            break;
        }
    }

    private Book getBookById(int id, ArrayList<Book> arrayBook) {
        for (int i = 0; i < arrayBook.size(); i++) {
            Book book = arrayBook.get(i); 

            if (book.getID() == id) {
                return book;
            }
        }
        return new Book(-1, "", "", 0);
    }

    private void borrowBook() {
        if (library.isEmpty()) {
            System.out.println("There are no books in the library yet.");
            return;
        }
        listAllBooks(library);

        System.out.print("Enter the book ID to borrow >>: ");
        while (true) {
            int id = numberInput();
            Book book = getBookById(id, library);
            if (book.getID() == -1) {
                System.out.print("This book does not exists. Try again >>: ");
                continue;
            }

            library.remove(book);
            borrowers.add(book);
            System.out.println("You have borrowed the book!");
            break;
        }
    }

    private void returnBook() {
        if (borrowers.isEmpty()) {
            System.out.println("You haven't borrowed a book yet.");
            return;
        }
        listAllBooks(borrowers);
        System.out.print("Enter the book ID to borrow >>: ");

        while (true) {
            int id = numberInput();
            Book book = getBookById(id, borrowers);
            if (book.getID() == -1) {
                System.out.print("This book does not exists. Try again >>: ");
                continue;
            }

            borrowers.remove(book);
            library.add(book);
            System.out.println("You have returned the book!");
            break;
        }
    }

    private void listAllBooks(ArrayList<Book> arrayBook) {
        for (int i = 0; i < arrayBook.size(); i++) {
            Book book = arrayBook.get(i);
            System.out.print("ID: " + book.getID() + "\t\t");
            System.out.print("Name: " + book.getName() + "\t\t");
            System.out.print("Author: " + book.getAuthor() + "\t\t");
            System.out.println("Published Year: " + book.getPublishedYear());
        }
    }


    // Helper functions
    private char charInput() {
        try {
            String input = reader.readLine().trim().toLowerCase();
            if (input.isEmpty()) {
                System.out.print("Input must not be empty >>: ");
                return charInput();
            }
            return input.charAt(0);
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            return charInput();
        }
    }

    private int numberInput() {
        try {
            String input = reader.readLine().trim();
            if (input.isEmpty()) {
                System.out.print("Input must not be empty >>: ");
                return numberInput();
            }
            int number = Integer.parseInt(input);
            return number;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            return numberInput();
        } catch (NumberFormatException e) {
            System.out.println("The input should be a number.");
            return numberInput();
        }
    }

    // "Remember folks, code duplication is a crime.
    // So I am a criminal."
    // - Ael
    private int yearInput() {
        try {
            String input = reader.readLine().trim();
            if (input.isEmpty()) {
                System.out.print("Input must not be empty >>: ");
                return yearInput();
            }
            if (!input.matches("[0-9]{4}")) {
                System.out.print("Input must be a number and format " +
                    "of: XXXX >>: ");
                return yearInput();
            }
            int number = Integer.parseInt(input);
            return number;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            return yearInput();
        } catch (NumberFormatException e) {
            System.out.println("The input should be a number.");
            return yearInput();
        }
    }

    private String stringInput() {
        try {
            String input = reader.readLine().trim();
            if (input.isEmpty()) {
                System.out.print("Input must not be empty >>: ");
                return stringInput();
            }
            return input;
        } catch (IOException e) {
            System.out.println("Something went wrong.");
            return stringInput();
        }
    }

    public static void main(String[] args)  {
        PracticePractice practice = new PracticePractice();

        while (practice.run()) {}
        System.out.println("Program Terminated!");
    }
}
