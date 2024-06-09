import java.util.*;

public class kruscal {
    static class DSU {
        int[] rankParent;
        int[] size;

        public DSU(int n) {
            rankParent = new int[n + 1];
            size = new int[n + 1];
            Arrays.fill(rankParent, -1);
            Arrays.fill(size, 1);
        }

        int find(int i) {
            if (rankParent[i] == -1)
                return i;
            return rankParent[i] = find(rankParent[i]);
        }

        boolean union(int a, int b) {
            int aParent = find(a);
            int bParent = find(b);
            if (aParent == bParent)
                return false;
            if (size[aParent] >= size[bParent]) {
                size[aParent] += size[bParent];
                rankParent[bParent] = aParent;
            } else {
                size[bParent] += size[aParent];
                rankParent[aParent] = bParent;
            }
            return true;
        }
    }

    static class Edge {
        int u, v, weight;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    static class ForestResult {
        List<List<Integer>> forest;
        DSU dsu;

        public ForestResult(List<List<Integer>> forest, DSU dsu) {
            this.forest = forest;
            this.dsu = dsu;
        }
    }

    public static ForestResult minimumSpanningForest(int N, List<Edge> edges) {
        bubbleSort(edges);
        DSU dsu = new DSU(N);
        List<List<Integer>> forest = new ArrayList<>();
        for (Edge edge : edges) {
            if (dsu.union(edge.u, edge.v)) {
                forest.add(Arrays.asList(edge.u, edge.v, edge.weight));
            }
        }
        return new ForestResult(forest, dsu);
    }

    static void bubbleSort(List<Edge> edges) {
        int n = edges.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (edges.get(j).weight > edges.get(j + 1).weight) {
                    Edge temp = edges.get(j);
                    edges.set(j, edges.get(j + 1));
                    edges.set(j + 1, temp);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int weight = scanner.nextInt();
            edges.add(new Edge(u, v, weight));
        }

        ForestResult result = minimumSpanningForest(N, edges);
        List<List<Integer>> forest = result.forest;
        DSU dsu = result.dsu;
        List<Set<Integer>> uniqueVertices = new ArrayList<>();
        for (List<Integer> tree : forest) {
            // create a set of unique vertices
            Set<Integer> componentVertices = new HashSet<>();

            // add unique vertices of each tree
            for (Integer vertex : tree) {
                componentVertices.add(dsu.find(vertex));
            }

            // save it
            uniqueVertices.add(componentVertices);
        }

        int numTrees = forest.size();

        System.out.println(numTrees);
        for (int i = 0; i < forest.size(); i++) {
            List<Integer> tree = forest.get(i);
            Set<Integer> componentVertices = uniqueVertices.get(i);
            System.out.println(componentVertices.size() + " " + tree.get(0));
            for (int j = 0; j < tree.size(); j++) {
                if (j == 0) {
                    System.out.print(tree.get(j));
                } else {
                    System.out.print(" " + tree.get(j) + " " + tree.get(++j));
                }
            }
            System.out.println();
        }
    }

}
