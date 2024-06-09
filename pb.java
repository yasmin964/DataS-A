import java.util.*;

public class Phonebook {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine();
        Map<String, List<String>> phoneBook = new HashMap<>(N);

        for (int i = 0; i < N; i++) {
            String input = scanner.nextLine();
            input = input.replace(',', ' ');
            // Split the input string by spaces
            String[] parts = input.split(" ");

            // Assuming you want to read three variables
            switch (parts[0]) {
                case "ADD":
                    if (parts.length == 3) {
                        String contactName = parts[1];
                        String phoneNumber = parts[2];
                        phoneBook.put(contactName, new ArrayList<>());
                        phoneBook.get(contactName).add(phoneNumber);
                    }
                    else if (parts.length == 4) {
                        String contactName = parts[1] + ' ' + parts[2];
                        String phoneNumber = parts[3];
                        phoneBook.put(contactName, new ArrayList<>());
                        phoneBook.get(contactName).add(phoneNumber);
                    }
                    break;
//                case "DELETE":
//                    if (parts.length == 3) {
//                        String contactName = parts[1];
//                        String phoneNumber = parts[2];
//                        phoneBook.remove(contactName, Collections.singletonList(phoneNumber));
//                    } else if (parts.length == 4) {
//                        String contactName = parts[1] + ' ' + parts[2];
//                        String phoneNumber = parts[3];
//                        phoneBook.remove(contactName, Collections.singletonList(phoneNumber));
//                    }
//                    break;
                case "FIND":
                    String contactName;
                    if (parts.length == 2) {
                        contactName = parts[1];
                    }
                    else{
                        contactName = parts[1] + ' ' + parts[2];
                    }
                    List<String> phoneNumbers = phoneBook.get(contactName);
                    if (phoneNumbers == null || phoneNumbers.isEmpty()) {
                        System.out.println("No contact info found for " + contactName);
                    } else {
                        System.out.print("Found " + phoneNumbers.size() + " phone numbers for " + contactName + ": ");
                        for (String phoneNumber : phoneNumbers) {
                            System.out.print(phoneNumber + " ");
                        }
                        System.out.println();
                    }
                    break;
            }
        }
    }
}

// Define the Map interface and HashMap class here
interface Map<K, V> {
    int size();

    boolean isEmpty();

    V get(K key);

    void put(K key, V value);

    void remove(K key);
}

class Entry<K, V> {
    K key;
    V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}

class HashMap<K, V> implements Map<K, V> {
    List<Entry<K, V>>[] hashTable;
    int capacity;
    int numberOfElements;

    public HashMap(int capacity) {
        this.capacity = capacity;
        this.numberOfElements = 0;
        this.hashTable = new List[capacity];
        for (int i = 0; i < capacity; i++) {
            this.hashTable[i] = new LinkedList<>();
        }
    }

    private Entry<K, V> getEntry(K key) {
        int hash = Math.abs(key.hashCode()) % capacity;
        for (Entry entry : hashTable[hash]) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public V get(K key) {
        int hash = Math.abs(key.hashCode()) % capacity;
        for (Entry<? extends K, ? extends V> entry : hashTable[hash]) {
            if (entry.key.equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int hash = Math.abs(key.hashCode()) % capacity;
        Entry<K, V> e = getEntry(key);
        if (e != null) {
            e.setValue(value);
        } else {
            this.hashTable[hash].add(new Entry<>(key, value));
            this.numberOfElements++;
        }
    }

    @Override
    public void remove(K key) {
        int hash = Math.abs(key.hashCode()) % capacity;
        Entry<K, V> e = null;
        for (Entry<K, V> entry : hashTable[hash]) {
            if (entry.getKey() == key) {
                e = entry;
            }
        }
        if (e != null) {
            hashTable[hash].remove(e);
            numberOfElements--;
        }
    }

    @Override
    public int size() {
        return this.numberOfElements;
    }

    @Override
    public boolean isEmpty() {
        return (this.numberOfElements == 0);
    }
}

