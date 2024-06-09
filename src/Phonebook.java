import java.util.*;

public class Phonebook {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        scanner.nextLine();
        Map<String, List<String>> phonebook = new HashMap<>(N);

        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine().replace(',', ' ');
            String[] parts = line.split(" ");
            if (parts[0].equals("ADD")) {
                if (parts.length==4) {
                    String name = parts[1] + ' ' + parts[2];
                    String phoneNumber = parts[3];
                    List<String> phoneNumbers = phonebook.get(name);
                    if (phoneNumbers == null) {
                        phoneNumbers = new ArrayList<>();
                    }
                    if(!phoneNumbers.contains(phoneNumber)) {
                        phoneNumbers.add(phoneNumber);
                        phonebook.put(name, phoneNumbers);
                    }
                }else if(parts.length==3){
                    String name = parts[1];
                    String phoneNumber = parts[2];
                    List<String> phoneNumbers = phonebook.get(name);
                    if (phoneNumbers == null) {
                        phoneNumbers = new ArrayList<>();
                    }
                    if(!phoneNumbers.contains(phoneNumber)) {
                        phoneNumbers.add(phoneNumber);
                        phonebook.put(name, phoneNumbers);
                    }
                }


            } else if (parts[0].equals("DELETE")) {
                if (parts.length == 2) { // If DELETE command has only 2 parts, delete entire contact
                    String name;
                    name = parts[1];
                    phonebook.remove(name);
                } else if (parts.length == 3) { // If DELETE command has 3 parts, delete specific phone number from contact
                    String name;
                    if (!parts[2].matches(".*\\d.*")) {
                        // Name has two parts
                        name = parts[1] + ' ' + parts[2];
                        phonebook.remove(name);
                    } else {
                        // Name has only one part
                        name = parts[1];
                        String phoneNumber = parts[2];
                        List<String> phoneNumbers = phonebook.get(name);
                        if (phoneNumbers != null) {
                            phoneNumbers.remove(phoneNumber);
                            if (phoneNumbers.isEmpty()) {
                                phonebook.remove(name); // Remove contact if it has no more phone numbers
                            }
                        }
                    }

                }else{
                    String name = parts[1] + ' ' + parts[2];
                    String phoneNumber = parts[3];
                    List<String> numbers = phonebook.get(name);
                    if (numbers != null) {
                        numbers.remove(phoneNumber);
                        if (numbers.isEmpty()) {
                            phonebook.remove(name);
                        }
                    }
                }
        } else if (parts[0].equals("FIND")) {
                if(parts.length==3) {
                    String name = parts[1] + ' ' + parts[2];
                    List<String> phoneNumbers = phonebook.get(name);
                    if (phoneNumbers == null || phoneNumbers.isEmpty()) {
                        System.out.println("No contact info found for " + name);
                    } else {
                        System.out.print("Found " + phoneNumbers.size() + " phone numbers for " + name + ": ");
                        for (String phoneNumber : phoneNumbers) {
                            System.out.print(phoneNumber + " ");
                        }
                        System.out.println();
                    }
                } else if (parts.length==2) {
                    String name = parts[1];
                    List<String> phoneNumbers = phonebook.get(name);
                    if (phoneNumbers == null || phoneNumbers.isEmpty()) {
                        System.out.println("No contact info found for " + name);
                    } else {
                        System.out.print("Found " + phoneNumbers.size() + " phone numbers for " + name + ": ");
                        for (String phoneNumber : phoneNumbers) {
                            System.out.print(phoneNumber + " ");
                        }
                        System.out.println();
                    }
                }
            }

        }
        scanner.close();

    }

    // Define the Map interface and HashMap class here
    interface Map<K, V> {
        int size();

        boolean isEmpty();

        V get(K key);

        void put(K key, V value);

        void remove(K key);
    }

    static class Entry<K, V> {
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

    static class HashMap<K, V> implements Map<K, V> {
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
            Iterator<Entry<K, V>> iterator = hashTable[hash].iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    iterator.remove();
                    numberOfElements--;
                    return;
                }
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
}

