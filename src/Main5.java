import java.util.*;

// Main class for managing the online bookstore
public class Main5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<User> subscribers = new ArrayList<>();
        PriceUpdateObserver priceUpdateNotifier = new PriceUpdateNotifier();

        String command;
        BookFactory bookFactory = new BookFactory();
        UserFactory userFactory = new UserFactory();

        while (!(command = scanner.nextLine()).equals("end")) {
            String[] tokens = command.split(" ");
            switch (tokens[0]) {
                case "createBook":
                    String bookTitle = tokens[1];
                    String bookAuthor = tokens[2];
                    double bookPrice = Double.parseDouble(tokens[3]);
                    bookFactory.createBook(bookTitle, bookAuthor, bookPrice);
                    continue;
                case "createUser":
                    String userType = tokens[1];
                    String username = tokens[2];
                    userFactory.createUser(userType, username);
                    continue;
                case "subscribe":
                    String subscriberName = tokens[1];
                    User subscriber = getUserByName(subscriberName, userFactory);
                    if (subscriber != null && !subscribers.contains(subscriber)) {
                        subscribers.add(subscriber);
                    }
                    subscriber.subscribe();
                    continue;
                case "unsubscribe":
                    String unsubscribedName = tokens[1];
                    User unsubscribedUser = getUserByName(unsubscribedName, userFactory);
                    if (subscribers.contains(unsubscribedUser)) {
                        subscribers.remove(unsubscribedUser);
                    }
                    unsubscribedUser.unsubscribe();
                    continue;
                case "updatePrice":
                    String updatedBookTitle = tokens[1];
                    String newPrice = tokens[2];
                    Book updatedBook = getBookByTitle(updatedBookTitle, bookFactory);
                    if (updatedBook != null) {
                        updatedBook.updatePrice(newPrice);
                        // Notify subscribers about price update
                        for (User subscriberr : subscribers) {
                            priceUpdateNotifier.notifyUser(subscriberr.getUsername(), updatedBookTitle, newPrice);
                        }
                    }
                    continue;
                case "readBook":
                    String readerName = tokens[1];
                    String bookToReadTitle = tokens[2];
                    Book bookToRead = getBookByTitle(bookToReadTitle, bookFactory);
                    User reader = getUserByName(readerName, userFactory);

                    if (bookToRead != null && bookToRead instanceof TextualBook) {
                        reader.readBook((TextualBook) bookToRead);
                    }

                    continue;
                case "listenBook":
                    String listenerName = tokens[1];
                    String bookToListenTitle = tokens[2];
                    Book bookToListen = getBookByTitle(bookToListenTitle, bookFactory);
                    User listener = getUserByName(listenerName, userFactory);


                    BookAccessProxy bookAccessProxy = new BookAccessProxy(bookToListen, listener);

                    if (listener instanceof PremiumUser){
                        listener.listenBook( bookToListen);
                        bookAccessProxy.updatePrice(bookToListen.getPrice());

                    } else {
                        System.out.println("No access");
                    }

                    break;
            }
        }
        scanner.close();
    }

    // Utility method to get a user by username
    private static User getUserByName(String userName, UserFactory userFactory) {
        if (userFactory.getUserRegistry().containsKey(userName)) {
            return userFactory.getUserRegistry().get(userName);
        } else {
            return null;
        }
    }

    // Utility method to get a book by title
    private static Book getBookByTitle(String title, BookFactory bookFactory) {
        if (bookFactory.getBookRegistry().containsKey(title)) {
            return bookFactory.getBookRegistry().get(title);
        } else {
            return null;
        }
    }
}

// Interface for the Book entity
interface Book {
    // return book tittle
    String getTitle();
    // return book author
    String getAuthor();
    // return book updated price
    void updatePrice(String newPrice);
    // return book price
    String getPrice();

}

// Concrete implementation of Book entity for Textual format
class TextualBook implements Book {
    private String title;
    private String author;
    private double price;

    public String getPrice() {
        return String.valueOf(price);
    }
    // Textual book constructor
    public TextualBook(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void updatePrice(String newPrice) {
        this.price = Double.parseDouble(newPrice); // Convert String to double
    }
}

// Concrete implementation of Book entity for Audio format
class AudioBook implements Book {
    private String title;
    private String author;
    private double price;

    public String getPrice() {
        return String.valueOf(price);
    }
    // Audio book constructor
    public AudioBook(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void updatePrice(String newPrice) {
        this.price = Double.parseDouble(newPrice); // Convert String to double
    }
}

// Interface for User entity
interface User {
    //return user name
    String getUsername();
    //operations that can be used by user
    void subscribe();
    void unsubscribe();
    void readBook(TextualBook book);
    void listenBook(Book book);
}

// Concrete implementation of User entity for Standard type
class StandardUser implements User {
    private String username;
    private boolean subscribed;
    // Standard user constructor
    public StandardUser(String username) {
        this.username = username;
        this.subscribed = false;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void subscribe() {
        if (!subscribed) {
            subscribed = true;
        } else {
            System.out.println("User already subscribed");
        }
    }

    @Override
    public void unsubscribe() {
        if (subscribed) {
            subscribed = false;
        } else {
            System.out.println("User is not subscribed");
        }
    }

    @Override
    public void readBook(TextualBook book) {
        System.out.println(username + " reading " + book.getTitle() + " by " + book.getAuthor());
    }

    @Override
    public void listenBook(Book book) {
        System.out.println("No access");
    }
}

// Concrete implementation of User entity for Premium type
class PremiumUser implements User {
    private String username;
    private boolean subscribed;
    //Premium user constructor
    public PremiumUser(String username) {
        this.username = username;
        this.subscribed = false;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void subscribe() {
        if (!subscribed) {
            subscribed = true;
        } else {
            System.out.println("User already subscribed");
        }
    }

    @Override
    public void unsubscribe() {
        if (subscribed) {
            subscribed = false;
        } else {
            System.out.println("User is not subscribed");
        }
    }

    @Override
    public void readBook(TextualBook book) {
        System.out.println(username + " reading " + book.getTitle() + " by " + book.getAuthor());
    }

    @Override
    public void listenBook(Book book) {
        System.out.println(username + " listening " + book.getTitle() + " by " + book.getAuthor());
    }
}

// Factory for creating instances of Book
class BookFactory {
    private Map<String, Book> bookRegistry; // Registry to keep track of existing books

    public BookFactory() {
        this.bookRegistry = new HashMap<>();
    }

    public Map<String, Book> getBookRegistry() {
        return bookRegistry;
    }

    public void createBook(String title, String author, double price) {
        if (bookRegistry.containsKey(title)) {
            System.out.println("Book already exists");
        } else {
            Book newBook = new TextualBook(title, author, price);
            bookRegistry.put(title, newBook);
        }
    }
}

// Proxy for controlling access to books based on user type
class BookAccessProxy implements Book {
    private Book realBook;
    private User user;

    public BookAccessProxy(Book realBook, User user) {
        this.realBook = realBook;
        this.user = user;
    }

    @Override
    public String getTitle() {
        return realBook.getTitle();
    }

    @Override
    public String getAuthor() {
        return realBook.getAuthor();
    }

    @Override
    public void updatePrice(String newPrice) {
        if (user instanceof PremiumUser) {
            realBook.updatePrice(newPrice);
        } else {
            System.out.println("No access"); // Standard users cannot update prices
        }
    }

    @Override
    public String getPrice() {
        return realBook.getPrice();
    }
}

// Observer interface for notifying users about book price updates
interface PriceUpdateObserver {
    void notifyUser(String username, String bookTitle, String newPrice);
}

// Concrete implementation of Observer for notifying users about book price updates
class PriceUpdateNotifier implements PriceUpdateObserver {
    @Override
    public void notifyUser(String username, String bookTitle, String newPrice){
        System.out.println(username + " notified about price update for " + bookTitle + " to " + newPrice);

    }
}

// Factory for creating instances of User
class UserFactory {
    // the data structure with user storage
    private Map<String, User> userRegistry;

    public UserFactory() {
        this.userRegistry = new HashMap<>();
    }
    // return user storage
    public Map<String, User> getUserRegistry() {
        return userRegistry;
    }
    // create user according to type of user
    public void createUser(String userType, String username) {
        if (userRegistry.containsKey(username)) {
            System.out.println("User already exists");
        } else {
            if (userType.equals("standard")) {
                User user = new StandardUser(username);
                userRegistry.put(username, user);
            } else if (userType.equals("premium")) {
                User user = new PremiumUser(username);
                userRegistry.put(username, user);
            } else {
                System.out.println("Invalid user type");
            }
        }
    }
}
