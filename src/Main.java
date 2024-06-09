//import java.util.*;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int N = scanner.nextInt(); // Number of vertices
//        Graph<Integer> graph = new Graph<>(); // Changed from Integer, Integer to Integer, Long
//        for (int i = 1; i <= N; i++) {
//            graph.addVertex(i); // Adding vertices
//        }
//        // Reading adjacency matrix and adding edges
//
//        for (int i = 1; i <= N; i++) {
//            for (int j = 1; j <= N; j++) {
//                long weight = scanner.nextLong(); // Changed from int to long
//                if (Math.abs(weight) <= 10000) { // Edge exists
//                    graph.addEdge(i,j, weight);
//                }
//            }
//        }
//        // Running Bellman-Ford algorithm
//        graph.bellmanFord(); // No need to pass source vertex as argument
//    }
//}
//
//class Graph<V> {
//    private final Map<Vertex<V>, List<Edge<V>>> adjacencyList;
//    public final Map<V, Vertex<V>> vertexMap;
//    private int size;
//
//    public Graph() {
//        this.adjacencyList = new HashMap<>();
//        this.vertexMap = new HashMap<>();
//        this.size = 0;
//    }
//
//    public void addVertex(V value) {
//        if (!vertexMap.containsKey(value)) {
//            Vertex<V> vertex = new Vertex<>(value);
//            adjacencyList.put(vertex, new ArrayList<>());
//            vertexMap.put(value, vertex);
//            size++;
//        }
//    }
//
//    public void addEdge(V fromValue, V toValue, long weight) {
//        Vertex<V> fromVertex = vertexMap.get(fromValue);
//        Vertex<V> toVertex = vertexMap.get(toValue);
//        if (fromVertex != null && toVertex != null) {
//            Edge<V> edge = new Edge<>(fromVertex, toVertex, weight);
//            adjacencyList.get(fromVertex).add(edge);
//            // For doubly-linked lists, add the reverse edge
//            adjacencyList.get(toVertex).add(new Edge<>(toVertex, fromVertex, weight));
//        }
//    }
//
//    public void bellmanFord() {
//        for (int k = 0; k < this.size; k++) {
//            Vertex<V> source = vertexMap.get(k);
//            initializeSingleSource(source);
//
//            for (int i = 1; i <= this.size - 1; i++) {
//                for (List<Edge<V>> edges : adjacencyList.values()) {
//                    for (Edge<V> edge : edges) {
//                        if (edge.weight != 100000) {
//                            relax(edge.getTo(), edge.getFrom(), edge.getWeight());
//                        }
//                    }
//                }
//            }
//
//            for (List<Edge<V>> edges : adjacencyList.values()) {
//                for (Edge<V> edge : edges) {
//                    if (edge.getTo().getDistance() > edge.getFrom().getDistance() + edge.getWeight()) {
//                        edge.getTo().setDistance(edge.getFrom().getDistance() + edge.getWeight());
//                        edge.getTo().setPredecessor(edge.getFrom());
//                        // Negative cycle found
//                        List<Vertex<V>> cycle = new ArrayList<>();
//                        Vertex<V> current = edge.getFrom().getPredecessor();
//
//                        while (current != null && !cycle.contains(current)) {
//                            cycle.add(current);
//                            current = current.getPredecessor();
//                        }
//                        System.out.println("YES");
//                        System.out.println(cycle.size());
//                        for (int i = cycle.size() - 1; i >= 0; i--) {
//                            System.out.print((cycle.get(i).getValue()) + " ");
//                        }
//                        System.out.println();
//                        return;
//                    }
//                }
//            }
//        }
//        // No negative cycle found
//        System.out.println("NO");
//    }
//
//    private void initializeSingleSource(Vertex<V> source) {
//        for (Vertex<V> vertex : adjacencyList.keySet()) {
//            vertex.setDistance(100000);
//            vertex.setPredecessor(null);
//        }
//        source.setDistance(0);
//    }
//
//    private void relax(Vertex<V> v, Vertex<V> u, long w) {
//        if (v.getDistance() > u.getDistance() + w) {
//            v.setDistance(u.getDistance() + w);
//            v.setPredecessor(u);
//        }
//    }
//
//    class Vertex<V> {
//        private final V value;
//        private long distance;
//        private Vertex<V> predecessor;
//
//        public Vertex(V value) {
//            this.value = value;
//            this.distance = Long.MAX_VALUE;
//            this.predecessor = null;
//        }
//
//        public V getValue() {
//            return value;
//        }
//
//        public long getDistance() {
//            return distance;
//        }
//
//        public void setDistance(long distance) {
//            this.distance = distance;
//        }
//
//        public Vertex<V> getPredecessor() {
//            return predecessor;
//        }
//
//        public void setPredecessor(Vertex<V> predecessor) {
//            this.predecessor = predecessor;
//        }
//    }
//
//    class Edge<V> {
//        private final Vertex<V> from;
//        private final Vertex<V> to;
//        private final long weight;
//
//        public Edge(Vertex<V> from, Vertex<V> to, long weight) {
//            this.from = from;
//            this.to = to;
//            this.weight = weight;
//        }
//
//        public Vertex<V> getFrom() {
//            return from;
//        }
//
//        public Vertex<V> getTo() {
//            return to;
//        }
//
//        public long getWeight() {
//            return weight;
//        }
//    }
//}
