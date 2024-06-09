////Yasmina Mamadalieva
//import java.util.*;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int N = scanner.nextInt(); // Number of vertices
//        Graph<Integer> graph = new Graph<>(N); // Changed from Integer, Integer to Integer, Long
//        for (int i = 0; i < N; i++) {
//            graph.addVertex(i); // Adding vertices
//        }
//        // Reading adjacency matrix and adding edges
//
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                long weight = scanner.nextLong(); // Changed from int to long
//                if (Math.abs(weight) <= 10000) { // Edge exists
//                    graph.addEdge(graph.vertices.get(i), graph.vertices.get(j), weight);
//                }
//            }
//        }
//        // Running Bellman-Ford algorithm
//        graph.YasminaMamadalieva_sp(); // No need to pass source vertex as argument
//    }
//}
//
//class Vertex<V> {
//    int value;
//    Vertex<V> predecessor;
//    long distance;
//    public Vertex(int value) {
//        this.value = value;
//    }
//}
//
//class Edge<V> {
//    Vertex<V> from;
//    Vertex<V> to;
//    long weight;
//    public Edge(Vertex<V> from, Vertex<V> to, long weight) {
//        this.from = from;
//        this.to = to;
//        this.weight = weight;
//    }
//}
//
//class Graph<V> {
//
//    private final List<Edge<V>> edges;
//    public final List<Vertex<V>> vertices;
//    private final int size;
//
//    public Graph(int size) {
//        this.edges = new ArrayList<>();
//        this.vertices = new ArrayList<>();
//        this.size = size;
//    }
//
//    void addVertex(int value) {
//        this.vertices.add(new Vertex<>(value));
//    }
//
//    void addEdge(Vertex<V> from, Vertex<V> to, long weight) {
//        this.edges.add(new Edge<>(from, to, weight));
//    }
//
//    void YasminaMamadalieva_sp() {
//        for (int k = 0; k < this.size; k++) {
//            Vertex<V> source = vertices.get(k); // Assuming the source vertex is the first vertex
//
//            this.initializeSingleSource(source);
//
//            for (int i = 1; i <= this.size - 1; i++) {
//                for (Edge<V> edge : this.edges) {
//                    if (edge.weight != 100000) {
//                        relax(edge.to, edge.from, edge.weight);
//                    }
//                }
//            }
//
//            // Check for negative cycles
//            for (Edge<V> edge : this.edges) {
//                Vertex<V> u = edge.from;
//                Vertex<V> v = edge.to;
//                long w = edge.weight;
//                if (v.distance > u.distance + w) {
//                    v.distance = u.distance + w; // Changed from int to long
//                    v.predecessor = u;
//                    // Negative cycle found
//                    List<Vertex<V>> cycle = new ArrayList<>();
//                    Vertex<V> current = edge.from.predecessor; // Start from the vertex before the starting vertex of the edge
//                    // Trace back through predecessors and add vertices to cycle list
//                    while (current != null && !cycle.contains(current)) {
//                        cycle.add(current);
//                        current = current.predecessor;
//                    }
//
//                    // Output the cycle
//                    System.out.println("YES");
//                    System.out.println(cycle.size());
//
//                    // Print the vertices in cycle order
//                    for (int i = cycle.size() - 1; i >= 0; i--) {
//                        System.out.print((cycle.get(i).value + 1) + " ");
//                    }
//                    System.out.println();
//                    return;
//                }
//            }
//        }
//        // No negative cycle found
//        System.out.println("NO");
//    }
//
//
//    private void initializeSingleSource(Vertex<V> source) {
//        for (Vertex<V> vertex : this.vertices) {
//            vertex.distance = 100000; // Changed from Integer.MAX_VALUE to Long.MAX_VALUE
//            vertex.predecessor = null;
//        }
//        source.distance = 0;
//    }
//
//    private void relax (Vertex<V> v, Vertex<V> u, long w) {
//        if (v.distance > u.distance + w) { // Changed from int to long
//            v.distance = u.distance + w; // Changed from int to long
//            v.predecessor = u;
//        }
//    }
//}
//
