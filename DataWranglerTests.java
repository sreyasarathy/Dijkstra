// --== CS400 Role Code Header ==--
// Name: Sreya Sarathy
// CSL Username: ssarathy
// Email: sarathy2@wisc.edu
// Team: DT Blue
// Lecture #: MWF Lecture 1 3:30 PM with Prof Florian
// Notes to Grader: <any optional extra notes to your grader>

/**
 * JUnit 5 tests for the Data Wrangler
 * @author sreyasarathy
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class DataWranglerTests {
    private DotReaderDW reader;

    /**
     * Test 1 uses the JUnit 5 framework and ensures that when an invalid filename is given,
     * an exception is thrown.
     * This is done using a lambda expression to simplify the process.
     * @throws FileNotFoundException
     */
    @Test
    public void test1() {
        // creating a new DotReaderDW Object
        reader = new DotReaderDW();

        // To ensure that program won't work when an invalid file is loaded by the user
        assertThrows(FileNotFoundException.class, () -> reader.readNodesFromDOT("invalidFile.dot"));
    }


    /**
     * Test 2 uses the JUnit 5 framework to test the getters, setters and the constructor for the
     * role code project from the networkPathFinderEdgeDW.java class
     * This is done by creating a sample networkPathFinderEdgeDW Object
     */
    @Test
    public void test2() {
        // creates a sample networkPathFinderEdgeDW object
        networkPathFinderEdgeDW sample = new networkPathFinderEdgeDW("Chicago", "Madison", 3);

        //check the getters and the constructors as a whole
        assertTrue(sample.getStart().equals("Chicago")); // start node
        assertTrue(sample.getEnd().equals("Madison")); // end node
        assertTrue(sample.getWeight() == 3); // weight

    }

    /**
     * Test 3 uses the JUnit 5 framework and tests the readNodesFromDot method.
     * Checks whether 15 nodes have been identified from the dot file.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void test3() throws FileNotFoundException {
        // creating a new DotReaderDW Object
        reader = new DotReaderDW();

        // to read the nodes and store them in a list
        List<String> readNodesFromDOT = reader.readNodesFromDOT("cities2023.dot");

        assertTrue(readNodesFromDOT.size() == 15);

    }


    /**
     * Test 4 uses the Junit 5 framework and tests the readEdgesFromDot method.
     * Checks whether there are 25 edges that have been identified from the dot file.
     */
    @Test
    public void test4() throws FileNotFoundException {
        // creating a new DotReaderDW Object
        reader = new DotReaderDW();

        // to read the edges and store them in a list
        List<networkPathFinderEdgeInterfaceDW> edges = reader.readEdgesFromDOT("cities2023.dot");

        Assertions.assertEquals(22, edges.size());
    }

    /**
     * Test 5 uses the JUnit 5 frameowkr and tests the DotReaderDW class
     * by reading edge information from a DOT file and checking the attributes
     * of the first edge.
     *
     * @throws FileNotFoundException if the specified DOT file cannot be found
     */
    @Test
    public void test5() throws FileNotFoundException {
        // creating a new DotReaderDW Object
        reader = new DotReaderDW();

        // to read the edges and store them in a list
        List<networkPathFinderEdgeInterfaceDW> edges = reader.readEdgesFromDOT("cities2023.dot");

        // The following lines of code check the attributes of the first edge.
        networkPathFinderEdgeInterfaceDW firstEdge = edges.get(0);
        assertEquals(firstEdge.getStart(), "Seattle");
        assertEquals(firstEdge.getEnd().replaceAll(">", ""), "Austin");
        assertEquals(firstEdge.getWeight(), 2);
    }

    /**
     * Test 6 uses the JUnit framework to test the DotReaderDW class
     * by reading node information from a DOT file,
     * checking the number of nodes and whether a specific node exists.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void test6() throws FileNotFoundException {
        // creating a new DotReaderDW Object
        reader = new DotReaderDW();

        // to read the nodes and store them in a list
        List<String> nodes = reader.readNodesFromDOT("cities2023.dot");

        assertEquals(15, nodes.size());

        // testing whether "Houston" is a city found in the dot file
        boolean sample = false;
        for (String node : nodes) {
            if (node.equals("Houston")) {
                sample = true;
                break;
            }
        }

        assertTrue(sample);
    }

    /** The following tests have been written during integration week.
     * There are 4 new testers - two to review the FrontendDeveloper
     * and two to show the integration process by the blue team.
     * The testers below depict the integration process by the blue team.
     */


    /**
     * Integration test 1 to check the shortest path cost between two nodes in a graph.
     * This is done using the nodes Seattle and Boston.
     * @throws FileNotFoundException if the cities2023.dot is not found.
     */
    @Test
    void integrationTest1() throws FileNotFoundException {
        // Creating the objects for this tester.
        NPFDijkstraGraphInterface graph = new NPFDijkstraGraph();
        DotReaderInterfaceDW reader = new DotReaderDW();
        Backend backend = new Backend(graph,reader);

        // Loading the dot file - cities2023.dot by using the backend load data method
        backend.loadData("cities2023.dot");

        // Using the AE's insertNode method to insert the nodes to be tested to the graph
        graph.insertNode("Seattle");
        graph.insertNode("Boston");

        // Testing whether the expectedCost is equal to the actual cost.
        // The expectedCost is calculated manually by looking at the dotFile.
        int expectedCost = 11;
        assertEquals(expectedCost,graph.shortestPathCost("Seattle", "Boston"));

    }

    /**
     * Integation Test 2 tests the ability to disable nodes and
     * still receive the shortest path between two nodes.
     * This is done by using the following nodes: Austin, Madison,
     * Milwaukee, Houston.
     * @throws FileNotFoundException - if cities2023.dot is not found.
     *
     */
    @Test
    void integrationTest2() throws FileNotFoundException {
        // Creating the objects for this tester.
        NPFDijkstraGraphInterface graph = new NPFDijkstraGraph();
        DotReaderInterfaceDW reader = new DotReaderDW();
        Backend backend = new Backend(graph,reader);

        // Loading the dot file - cities2023.dot by using the backend load data method
        backend.loadData("cities2023.dot");

        // Using the AE's insertNode method to insert the nodes to be tested to the graph
        graph.insertNode("Austin"); // Node A
        graph.insertNode("Madison"); // Node B
        graph.insertNode("Milwaukee"); // Node C
        graph.insertNode("Houston"); // Node D

        // Testing whether we can disable nodes and still receive the shortestPath.
        List<String> disabledNodes = Arrays.asList("Madison");
        List<String> shortestPath = graph.shortestPathData("Austin", "Houston", disabledNodes);
        List<String> expectedPath = Arrays.asList("Austin", "Milwaukee", "Houston");
        assertEquals(expectedPath, shortestPath);
}

    /**
     * The below two testers are CodeReviewTesters.
     * The first testers uses the JUnit 5 framework to test whether the correct error message is displayed
     * when the user presses a "letter" that is not set to any of the commands.
     */
    @Test
    void CodeReviewOfFrontendDeveloper1() throws Exception {
        // Creating the objects for this tester
        TextUITester tester = new TextUITester("Z\nQ/n");
        Scanner scanner = new Scanner(System.in);
        NetworkPathFrontendFD frontend = new NetworkPathFrontendFD(scanner, new Backend(new NPFDijkstraGraph(),
                new DotReaderDW()));

        // Running the command loop after making all the objects for this method.
        frontend.runCommandLoop();

        // To check whether the output is the same as the expected output.
        String output = tester.checkOutput();
        assertTrue(output.contains("Didn't recognize that command.  Please type one of the letters presented within " +
                "[]s to identify the command you would like to choose."));
    }

    /**
     * The second testers uses the JUnit 5 framework to test whether the correct display message is seen to the user
     * when they quit using the program.
     */
    @Test
    void CodeReviewOfFrotendDeveloper2() {
        // Creating the objects for this tester
        TextUITester tester2 = new TextUITester("Q/n");
        Scanner scanner = new Scanner(System.in);
        NetworkPathFrontendFD frontend = new NetworkPathFrontendFD(scanner, new Backend
                (new NPFDijkstraGraph(), new DotReaderDW()));

            // Running the command loop after making all the objects for this method.
            frontend.runCommandLoop();

            // Now we check that the thank you - return message prints correctly
            String output = tester2.checkOutput();
            assertTrue(output.contains("---------------------------------------------------------------------------" +
                    "----"));
            assertTrue(output.contains("Thank you for using the Network Path Finder App."));

    }

}







