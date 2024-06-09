import java.util.*;
import java.io.*;

public class train {
    public static void main(String[] args) {
        try {
            // Create a File object representing input.txt
            File file = new File("input.txt");

            // Create a Scanner to read from the file
            Scanner scanner = new Scanner(file);
            // Read input
            Map<String, String> input = readInput(scanner);


            // Extract input data
            String type = input.get("type").replaceAll("\\[|\\]", "");
            String statesStr = input.get("states").replaceAll("\\[|\\]", "");
            List<String> states = Arrays.asList(statesStr.split("\\s*,\\s*")); // Split by comma with optional whitespace

            String alphabetStr = input.get("alphabet").replaceAll("\\[|\\]", "");
            List<String> alphabet = Arrays.asList(alphabetStr.split(","));
            String initial = input.get("initial").replaceAll("\\[|\\]", "");
            String acceptingStr = input.get("accepting").replaceAll("\\[|\\]", "");
            List<String> accepting = Arrays.asList(acceptingStr.split(","));
            String transitionsStr = input.get("transitions").replaceAll("\\[|\\]", "");
            List<String> transitions = Arrays.asList(transitionsStr.split(","));
            // Generate regular expression
            String validationResult = validateInput(input);
            if (!validationResult.equals("Valid")) {
                System.out.println(validationResult);
                return;
            }

            String regex = generateRegExp(type, states, alphabet, initial, accepting, transitions);
            System.out.println(regex);


            // Close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            e.printStackTrace();
        }

    }

    // Read input from scanner and store it in a map
    private static Map<String, String> readInput(Scanner scanner) {
        Map<String, String> input = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                input.put(parts[0].trim(), parts[1].trim());
            } else {
                System.err.println("Invalid input format: " + line);
            }
        }
        return input;
    }


    // Validate the input according to the specified rules
    private static String validateInput(Map<String, String> input) {
        if (!input.containsKey("type") || !input.containsKey("states") || !input.containsKey("alphabet")
                || !input.containsKey("initial") || !input.containsKey("accepting") || !input.containsKey("transitions")) {
            return "E1: Input file is malformed";
        }

        String type = input.get("type").replaceAll("\\[|\\]", "");
        List<String> states = new ArrayList<>(Arrays.asList(input.get("states").split(",")));
        String alphabetStr = input.get("alphabet").replaceAll("\\[|\\]", "");
        List<String> alphabet = Arrays.asList(alphabetStr.split(","));

        String initial = input.get("initial").replaceAll("\\[|\\]", "");
        if (initial != null) {
            states.add(initial);
        } else {
            return "E2: Initial state is not defined";
        }
        String acceptingsStr = input.get("accepting").replaceAll("\\[|\\]", "");
        List<String> accepting = Arrays.asList(acceptingsStr.split(","));

        String transitionsStr = input.get("transitions").replaceAll("\\[|\\]", "");
        List<String> transitions = Arrays.asList(transitionsStr.split(","));

        if (accepting.isEmpty()) return "E3: Set of accepting states is empty";

        String validateStatesResult = validateStates(states);
        if (!validateStatesResult.equals("Valid")) {
            return validateStatesResult;
        }

        if (type.equals("deterministic")||type.equals("non-deterministic")) {
            for (String accept : accepting){
                if (!isConnected(states, transitions, initial, accept)) {
                    return "E6: Some states are disjoint";
                }
            }
        }
        return "Valid";
    }

    private static boolean isConnected(List<String> states, List<String> transitions, String state1, String state2) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(state1);

        while (!queue.isEmpty()) {
            String currentState = queue.poll();
            visited.add(currentState);

            if (currentState.equals(state2)) {
                return true; // state1 and state2 are connected
            }

            for (String transition : transitions) {
                String[] parts = transition.split(">");
                if (parts[0].trim().equals(currentState)) {
                    String nextState = parts[2].trim(); // Changed to parts[2] to get the destination state
                    if (!visited.contains(nextState)) {
                        queue.offer(nextState);
                    }
                }
            }
        }

        return false; // state1 and state2 are not connected
    }

    private static String validateStates(List<String> states) {
        for (String state : states) {
            String trimmedState = state.trim().replaceAll("\\[|\\]", "");
            if (!containsOnly(trimmedState, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_")) {
                return "E4: A state '" + state + "' is not in the set of states";
            }
        }
        return "Valid";
    }

    private static boolean containsOnly(String str, String validChars) {
        for (int i = 0; i < str.length(); i++) {
            if (validChars.indexOf(str.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }


    private static String generateRegExp(String type, List<String> states, List<String> alphabet,
                                         String initial, List<String> accepting, List<String> transitions) {
        int n = states.size();
        String[][][] regexMatrix = new String[n][n][n + 1];
        Map<String, String> elements = new HashMap<>();

        // Populate matrix with transitions
        for (String transition : transitions) {
            String[] parts = transition.trim().split(">");
            int i = states.indexOf(parts[0].trim());
            int j = states.indexOf(parts[2].trim());
            String a = parts[1].trim();
            if(i==j ){
                regexMatrix[i][j][0] = "(" + a + "|eps)";
                elements.put("R"+i+j, "(" + a + "|eps)");
            }
            else{
                regexMatrix[i][j][0] = "(" + a + ")";
                elements.put("R"+i+j, "(" +a+")");
            }

        }
        // Initialize base cases
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k <= n; k++) {
                    if (!elements.containsKey("R" + i + j)) {
                        if (i == j && k == 0) {
                            regexMatrix[i][j][0] = "(eps)";
                            elements.put("R"+i+j,"eps" );

                        } else {
                            regexMatrix[i][j][0] = "({})";
                            elements.put("R"+i+j, "({})" );

                        }
                    }
                }
            }
        }
        for (int k = 0; k < n; k++) {
            String[][] buff = new String[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    String left = regexMatrix[i][k][k];
                    String right = regexMatrix[k][j][k];
                    String middle = regexMatrix[k][k][k];
                    String after = regexMatrix[i][j][k];
                    buff[i][j] = "(" + left + middle + "*" + right + "|" + after + ")";
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    regexMatrix[i][j][k + 1] = buff[i][j];
                }
            }
        }

        // Extract final regular expression for accepting states
        StringBuilder regexBuilder = new StringBuilder();
        for (String acceptState : accepting) {
            int i = states.indexOf(acceptState);
            int j = states.indexOf(initial);
            regexBuilder.append(regexMatrix[j][i][n]).append("|");
        }

        return regexBuilder.toString().replaceAll("\\|$", "");
    }

}
