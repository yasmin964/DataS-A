// Floyd Warshall Algorithm in Java

class FloydWarshall {
    final static int INF = 9999, nV = 5;

    // Implementing floyd warshall algorithm
    void floydWarshall(int graph[][]) {
        int matrix[][] = new int[nV][nV];
        int i, j, k;

        for (i = 0; i < nV; i++)
            for (j = 0; j < nV; j++)
                matrix[i][j] = graph[i][j];

        // Adding vertices individually
        for (k = 0; k < nV; k++) {
            for (i = 0; i < nV; i++) {
                for (j = 0; j < nV; j++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j])
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                }
            }
            System.out.println("new");
            printMatrix(matrix);
        }
        System.out.println("final");
        printMatrix(matrix);
    }

    void printMatrix(int matrix[][]) {
        for (int i = 0; i < nV; ++i) {
            for (int j = 0; j < nV; ++j) {
                if (matrix[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int graph[][] = { { 0, 2, INF, -5, INF },
                { INF, 0, 3, 8, -4 },
                { 1, INF, 0, INF, 7 },
                { INF, INF, 4, 0, INF},
                {6, INF, INF, INF, 0 }};
        FloydWarshall a = new FloydWarshall();
        a.floydWarshall(graph);
    }
}