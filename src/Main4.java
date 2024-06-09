//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.*;
//
//public class Main4
//{
//    public static void main(String[] args) {
//        try {
//            List<String> lines = readInput("C:/Users/User/IdeaProjects/untitled12/src/input.txt");
//            Map<String, String> fsaDescription = parseInput(lines);
//            String regExp = translateToFSARegExp(fsaDescription);
//            System.out.println(regExp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static List<String> readInput(String fileName) throws IOException {
//        List<String> lines = new ArrayList<>();
//        BufferedReader reader = new BufferedReader(new FileReader(fileName));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            lines.add(line);
//        }
//        reader.close();
//        return lines;
//    }
//
//    private static Map<String, String> parseInput(List<String> lines) {
//        Map<String, String> fsaDescription = new HashMap<>();
//        for (String line : lines) {
//            String[] parts = line.split("=");
//            fsaDescription.put(parts[0], parts[1].replaceAll("\\[|\\]", ""));
//        }
//        return fsaDescription;
//    }
//
//    private static String translateToFSARegExp(Map<String, String> fsaDescription) {
//        String type = fsaDescription.get("type");
//        String states = fsaDescription.get("states");
//        String alphabet = fsaDescription.get("alphabet");
//        String initial = fsaDescription.get("initial");
//        String accepting = fsaDescription.get("accepting");
//        String transitions = fsaDescription.get("transitions");
//
//        // Validate FSA description
//        if (!type.equals("deterministic") && !type.equals("non-deterministic")) {
//            return "E1: Input file is malformed";
//        }
//        if (!fsaDescription.containsKey("initial")) {
//            return "E2: Initial state is not defined";
//        }
//        if (accepting.isEmpty()) {
//            return "E3: Set of accepting states is empty";
//        }
//        String validateStatesResult = validateStates(Arrays.asList(fsaDescription.get("states").split(",")));
//        if (!validateStatesResult.equals("Valid")) {
//            return validateStatesResult;
//        }
//        for (String transition : transitions.split(",")) {
//            String[] parts = transition.split(">");
//            String[] transParts = parts[1].split("\\|");
//            for (String trans : transParts) {
//                if (!alphabet.contains(trans)) {
//                    return "E5: A transition '" + trans + "' is not represented in the alphabet";
//                }
//            }
//        }
//
//        // Apply Kleene's algorithm
//        if (type.equals("deterministic")) {
//            return "Kleene's algorithm not applicable for deterministic FSAs";
//        } else {
//            String regExp = applyKleenesAlgorithm(states, alphabet, initial, accepting, transitions);
//            return regExp;
//        }
//    }
//    private static String validateStates(List<String> states) {
//        for (String state : states) {
//            // Remove brackets from the state string
//            String trimmedState = state.trim().replaceAll("\\[|\\]", "");
//            if (!containsOnly(trimmedState, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_")) {
//                return "E4: A state '" + state + "' is not in the set of states";
//            }
//        }
//        return "Valid";
//    }
//
//    private static boolean containsOnly(String str, String validChars) {
//        for (int i = 0; i < str.length(); i++) {
//            if (validChars.indexOf(str.charAt(i)) == -1) {
//                return false;
//            }
//        }
//        return true;
//    }
//    private static String applyKleenesAlgorithm(String states, String alphabet, String initial, String accepting, String transitions) {
//        String[] stateArr = states.split(",");
//        String[][][] regExpMatrix = new String[stateArr.length][stateArr.length][alphabet.length()];
//
//        // Initialize R-1 matrix
//        for (int i = 0; i < stateArr.length; i++) {
//            for (int j = 0; j < stateArr.length; j++) {
//                for (int k = 0; k < alphabet.length(); k++) {
//                    if (i != j) {
//                        regExpMatrix[i][j][k] = alphabet.charAt(k) + "|eps";
//                    } else {
//                        regExpMatrix[i][j][k] = alphabet.charAt(k) + "|eps";
//                    }
//                }
//            }
//        }
//
//        // Apply Kleene's algorithm
//        for (int k = 0; k < stateArr.length; k++) {
//            for (int i = 0; i < stateArr.length; i++) {
//                for (int j = 0; j < stateArr.length; j++) {
//                    StringBuilder regExp = new StringBuilder();
//                    for (int m = 0; m < stateArr.length; m++) {
//                        regExp.append("(").append(regExpMatrix[i][m][0]).append(regExpMatrix[m][m][0]).append(")*").append(regExpMatrix[m][j][0]).append("|");
//                    }
//                    regExp.deleteCharAt(regExp.length() - 1); // Remove the last "|"
//                    regExpMatrix[i][j][0] = regExp.toString();
//                }
//            }
//        }
//
//        // Combine regular expressions for accepting states
//        StringBuilder resultRegExp = new StringBuilder();
//        String[] acceptStates = accepting.split(",");
//        for (String acceptState : acceptStates) {
//            resultRegExp.append(regExpMatrix[Integer.parseInt(initial)][Integer.parseInt(acceptState)][0]).append("|");
//        }
//        resultRegExp.deleteCharAt(resultRegExp.length() - 1); // Remove the last "|"
//
//        return resultRegExp.toString();
//    }
//
//}
