//Yasmina Mamadalieva
/*
GFG class was implemented inspiring by https://www.geeksforgeeks.org/priority-queue-using-binary-heap/
 */
import java.util.*;

public class djkstra {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(); // Number of vertices
        int M = scanner.nextInt(); // Number of edges

        Graph<Integer> graph = new Graph<>();

        for (int i = 0; i < N; i++) {
            graph.addVertex(i);
        }

        for (int i = 0; i < M; i++) {
            int from = scanner.nextInt() - 1;
            int to = scanner.nextInt() - 1;
            long length = scanner.nextLong();
            long bandwidth = scanner.nextLong();
            graph.addEdge(graph.vertices.get(from), graph.vertices.get(to), length, bandwidth);
        }

        int start = scanner.nextInt() - 1;
        int end = scanner.nextInt() - 1;
        long minBandwidth = scanner.nextLong();
        graph.setMinBandwidth(minBandwidth);

        graph.YasminaMamadalieva_sp(start, end);
    }
}

class Vertex<V> {
    int value;
    Vertex<V> predecessor;
    long distance;
    boolean visited;
    long bandwidth = 10000;

    ArrayList<Edge<V>> adjacent;

    public Vertex(int value) {
        this.value = value;
        this.adjacent = new ArrayList<>();
    }
}

class Edge<V> {
    Vertex<V> from;
    Vertex<V> to;
    long weight;
    long bandwidth;

    public Edge(Vertex<V> from, Vertex<V> to, long weight, long bandwidth) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.bandwidth = bandwidth;
    }
}

class Graph<V> {

    private final List<Edge<V>> edges;

    public final List<Vertex<V>> vertices;
    ArrayList<ArrayList<Edge<V>>> adjList;
    long minBandwidth;

    public Graph() {
        this.edges = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.adjList = new ArrayList<>();
    }

    public void setMinBandwidth(long minBandwidth) {
        this.minBandwidth = minBandwidth;
    }

    void addVertex(int value) {
        this.vertices.add(new Vertex<>(value));
    }

    void addEdge(Vertex<V> from, Vertex<V> to, long weight, long bandwidth) {
        Edge<V> edge = new Edge<>(from, to, weight, bandwidth);
        this.edges.add(edge);
        Vertex<V> vertex = this.vertices.get(from.value);
        vertex.adjacent.add(edge);

        // Ensure adjacency lists are initialized for both 'from' and 'to' vertices
        while (adjList.size() <= Math.max(from.value, to.value)) {
            adjList.add(new ArrayList<>());
        }
        adjList.get(from.value).add(edge);
    }

    void YasminaMamadalieva_sp(int start, int end) {
        for (Vertex<V> vertex : vertices) {
            vertex.distance = Long.MAX_VALUE;
            vertex.predecessor = null;
            vertex.visited = false;
        }

        // Custom priority queue implementation
        GFG<Vertex<V>> priorityQueue = new GFG<>();

        vertices.get(start).distance = 0;
        priorityQueue.insert((Vertex<Vertex<V>>) vertices.get(start));

        while (!priorityQueue.isEmpty()) {
            Vertex<V> u = (Vertex<V>) priorityQueue.extractMax();
            u.visited = true;
            for (Edge<V> e : this.adjList.get(u.value)) {
                Vertex<V> v = e.to;
                if (!v.visited && e.bandwidth >= minBandwidth) {
                    long alt = u.distance + e.weight;
                    if (alt < v.distance) {
                        v.distance = alt;
                        v.predecessor = u;
                        v.bandwidth = e.bandwidth;
                        priorityQueue.insert((Vertex<Vertex<V>>) v);
                    }
                }
            }
        }

        List<Vertex<V>> path = new ArrayList<>();
        long resultBandwidth = 10000;
        Vertex<V> current = vertices.get(end);
        while (current != null) {
            path.add(current);
            resultBandwidth = Math.min(resultBandwidth, current.bandwidth);
            current = current.predecessor;
        }
        Collections.reverse(path);

        if (this.vertices.get(end).distance >= 10000) {
            System.out.println("IMPOSSIBLE");
        } else {
            System.out.println(path.size() + " " + this.vertices.get(end).distance + " " + resultBandwidth);
            for (Vertex<V> vertex : path) {
                System.out.print((vertex.value + 1) + " ");
            }
            System.out.println();
        }
    }

    // Custom priority queue implementation
    class GFG<T> {

        ArrayList<Vertex<T>> H = new ArrayList<>();
        int size = -1;

        // Function to return the index of the parent node of a given node
        int parent(int i) {
            return (i - 1) / 2;
        }

        // Function to return the index of the left child of the given node
        int leftChild(int i) {
            return ((2 * i) + 1);
        }

        // Function to return the index of the right child of the given node
        int rightChild(int i) {
            return ((2 * i) + 2);
        }

        // Function to shift up the node in order to maintain the heap property
        void shiftUp(int i) {
            while (i > 0 && H.get(parent(i)).distance < H.get(i).distance) {
                swap(parent(i), i);
                i = parent(i);
            }
        }

        // Function to shift down the node in order to maintain the heap property
        void shiftDown(int i) {
            int maxIndex = i;
            int l = leftChild(i);
            if (l <= size && H.get(l).distance > H.get(maxIndex).distance) {
                maxIndex = l;
            }
            int r = rightChild(i);
            if (r <= size && H.get(r).distance > H.get(maxIndex).distance) {
                maxIndex = r;
            }
            if (i != maxIndex) {
                swap(i, maxIndex);
                shiftDown(maxIndex);
            }
        }

        // Function to insert a new element in the binary heap
        void insert(Vertex<T> p) {
            size = size + 1;
            H.add(p);
            shiftUp(size);
        }

        // Function to extract the element with maximum priority
        Vertex<T> extractMax() {
            Vertex<T> result = H.get(0);
            H.set(0, H.get(size));
            size = size - 1;
            shiftDown(0);
            return result;
        }

        // Function to swap two elements in the heap
        void swap(int i, int j) {
            Vertex<T> temp = H.get(i);
            H.set(i, H.get(j));
            H.set(j, temp);
        }

        // Function to check if the heap is empty
        boolean isEmpty() {
            return size == -1;
        }
    }
}
