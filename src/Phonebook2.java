//Ksenia Korchagina
import java.util.Collections;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Phonebook2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Map<String, List<String>> phoneBook = new HashMap<>(10000);

        for (int i = 0; i < N; i++) {
            String input = scanner.nextLine();
            input = input.replace(',', ' ');
            String[] parts = input.split(" ");

            switch (parts[0]) {
                case "ADD":
                    if (parts.length == 3) {
                        String contactName = parts[1];
                        String phoneNumber = parts[2];
                        if (!((HashMap<String, List<String>>) phoneBook).phoneNumberExists(contactName, phoneNumber)) {
                            ((HashMap<String, List<String>>) phoneBook).putIfAbsent(contactName, new ArrayList<>());
                            phoneBook.get(contactName).add(phoneNumber);
                        }
                    } else if (parts.length == 4) {
                        String contactName = parts[1] + ' ' + parts[2];
                        String phoneNumber = parts[3];
                        if (!((HashMap<String, List<String>>) phoneBook).phoneNumberExists(contactName, phoneNumber)) {
                            ((HashMap<String, List<String>>) phoneBook).putIfAbsent(contactName, new ArrayList<>());
                            phoneBook.get(contactName).add(phoneNumber);
                        }
                    }
                    break;
                case "DELETE":
                    if (parts.length == 2) {
                        String contactName = parts[1];
                        phoneBook.remove(contactName);
                    } else if (parts.length == 3) {
                        if (containsDigits(parts[2])) {
                            String contactName = parts[1];
                            String phoneNumber = parts[2];
                            List<String> numbers = phoneBook.get(contactName);
                            if (numbers != null) {
                                numbers.remove(phoneNumber);
                                if (numbers.isEmpty()) {
                                    phoneBook.remove(contactName);
                                }
                            }
                        } else {
                            String contactName = parts[1] + ' ' + parts[2];
                            phoneBook.remove(contactName);
                        }
                    } else if (parts.length == 4) {
                        String contactName = parts[1] + ' ' + parts[2];
                        String phoneNumber = parts[3];
                        List<String> numbers = phoneBook.get(contactName);
                        if (numbers != null) {
                            numbers.remove(phoneNumber);
                            if (numbers.isEmpty()) {
                                phoneBook.remove(contactName);
                            }
                        }
                    }
                    break;
                case "FIND":
                    String contactName = parts.length == 2 ? parts[1] : parts[1] + ' ' + parts[2];
                    List<String> phoneNumbers = ((HashMap<String, List<String>>) phoneBook).getOrDefault(contactName, Collections.emptyList());
                    if (phoneNumbers.isEmpty()) {
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

    public static boolean containsDigits(String input) {
        return input.matches(".*\\d.*");
    }
}



interface Map<K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    void search(K key);
    int size();
    boolean isEmpty();
}

class KeyValuePair<K, V> {
    K key;
    V value;

    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

class HashMap<K, V> implements Map<K, V> {
    List<KeyValuePair<K, V>>[] hashTable;
    int capacity;
    int numberOfElements;

    public HashMap(int capacity) {
        this.capacity = capacity;
        this.numberOfElements = 0;
        this.hashTable = new List[capacity];
        for (int i = 0; i < capacity; i++) {
            this.hashTable[i] = new ArrayList<>();
        }
    }

    public int hash(K key) {
        int i = key.hashCode() % this.capacity;
        if (i < 0) {
            i *= -1;
        }
        return i;
    }

    @Override
    public V get(K key) {
        for (KeyValuePair<K, V> kv : this.hashTable[hash(key)]) {
            if (kv.key.equals(key)) {
                return kv.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        for (KeyValuePair<K, V> kv : this.hashTable[hash(key)]) {
            if (kv.key.equals(key)) {
                kv.value = value;
                return;
            }
        }
        this.hashTable[hash(key)].add(new KeyValuePair<>(key, value));
        this.numberOfElements++;
    }

    @Override
    public void remove(K key) {
        for (KeyValuePair<K, V> kv : this.hashTable[hash(key)]) {
            if (kv.key.equals(key)) {
                kv.value = null;
                this.numberOfElements--;
                return;
            }
        }
    }

    @Override
    public void search(K key) {
        for (List<KeyValuePair<K, V>> list : this.hashTable) {
            for (KeyValuePair<K, V> pair : list) {
                if (pair.key.equals(key)) {
                    System.out.println("Key " + key + " found with value: " + pair.value);
                    return;
                }
            }
        }
        System.out.println("Key " + key + " not found.");
    }

    @Override
    public int size() {
        return this.numberOfElements;
    }

    @Override
    public boolean isEmpty() {
        return (this.numberOfElements == 0);
    }
    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return (value != null) ? value : defaultValue;
    }

    public void putIfAbsent(K key, V value) {
        if (get(key) == null) {
            put(key, value);
        }
    }

    // Custom method to check if a phone number already exists for a contact
    public boolean phoneNumberExists(String contactName, String phoneNumber) {
        List<String> numbers = (List<String>) getOrDefault((K) contactName, (V) Collections.emptyList());
        return numbers.contains(phoneNumber);
    }
}

