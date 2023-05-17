// --== CS400 File Header Information ==--
// Name: Naman Parekh
// Email: ncparekh@wisc.edu
// Group and Team: DT
// Group TA: Daniel Finer
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import java.util.Scanner;

/**
 * Main entry point for starting and running the NPFGraph App.
 *
 * @author AlgorithmEngineer.
 */
public class NPFGraphApp {

    /**
     * Currently only provided a skeleton layout of the main App class which will be commented out
     * during integration week when all group mates will be able to merge their code into main.
     *
     */
    public static void main(String[] args) {

        // Use data wrangler's code to load post data
        DotReaderInterfaceDW graphLoader = new DotReaderDW();

        // Use algorithm engineer's code to store and search for data
        NPFDijkstraGraphInterface graph;
        graph = new NPFDijkstraGraph();

        // Use the backend developer's code to manage all app specific processing
        BackendInterface backend = new Backend(graph, graphLoader);

        // Use the frontend developer's code to drive the text-base user interface
        Scanner scanner = new Scanner(System.in);
        FrontendInterface frontend = new NetworkPathFrontendFD(scanner, backend);
        frontend.runCommandLoop();


    }
}
