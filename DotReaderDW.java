// --== CS400 Role Code Header ==--
// Name: Sreya Sarathy
// CSL Username: ssarathy
// Email: sarathy2@wisc.edu
// Team: DT Blue
// Lecture #: MWF Lecture 1 3:30 PM with Prof Florian
// Notes to Grader: <any optional extra notes to your grader>

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * A class that implements the DotReaderInterfaceDW interface.
 * This class provides a method to read nodes and edges from the DOT File.
 * @author sreyasarathy
 */

public class DotReaderDW implements DotReaderInterfaceDW {


    // The constructor
    public DotReaderDW() {
    }

    /**
     * Reads in a DOT file and returns a list of all the node names in the file.
     * @param filename cities2023.dot
     * @return nodes
     * @throws FileNotFoundException - if cities2023.dot is not found
     */
    @Override
    public List<String> readNodesFromDOT(String filename) throws FileNotFoundException {

        // Creating the objects fot this method
        List<String> nodes = new ArrayList<>();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // White spaces, ->, digraph G{, } and comments (//) should be ignored from the dot file - cities2023.dot
            if (line.startsWith("//") || line.isEmpty() || line.contains("->") || line.equals("digraph G {") ||
                    line.equals("}")) {
                continue;
            }

            String[] cities = line.split(";");
            String cityNode = cities[0].trim();
            if (!cityNode.isEmpty()) {
                nodes.add(cityNode);
            }
        }

        scanner.close();

        return nodes;
    }


    /**
     *  Reads in a DOT file and returns a list of all the edges and their weights in the file.
     * @param filename cities2023.dot
     * @return edge
     * @throws FileNotFoundException - if cities2023.dot is not found
     */
    @Override
    public List<networkPathFinderEdgeInterfaceDW> readEdgesFromDOT(String filename) throws FileNotFoundException {
        
        // Creating the objects fot this method
        List<networkPathFinderEdgeInterfaceDW> edges = new ArrayList<>();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        boolean directedGraph = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("//") || line.isEmpty()) { // to ignore comments and empty lines
                continue;
            }

            if (line.startsWith("digraph")) {
                directedGraph = true;
                continue;
            }

            // to ignore -> and --
            String remove = directedGraph ? "->" : "--";
            if (line.contains(remove)) {
                String[] parts = line.split(Pattern.quote(remove));
                String start = parts[0].trim();
                String end;

                if (parts.length == 2 && parts[1].contains("[")) {
                    end = parts[1].split("\\[")[0].trim();
                } else {
                    end = parts[1].trim();
                }

                int weight;
                if (parts.length >= 2 && parts[1].contains("label=\"")) {
                    weight = Integer.parseInt(parts[1].split("label=\"")[1].split("\"")[0]);
                } else {
                    weight = 0;
                }
                networkPathFinderEdgeDW edge = new networkPathFinderEdgeDW(start, end, weight);
                edges.add(edge);
            }
        }

        scanner.close();

        return edges;

    }
}
