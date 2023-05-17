import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Backend class which implements interface methods which utilize algorithm engineer
 * and data wrangler functionality to respond to frontend requests about the graph
 * which is built from the data wrangler's data
 */
public class Backend implements BackendInterface {
    private List<String> nodes; // stores all graph nodes
    private List<networkPathFinderEdgeInterfaceDW> edges; // stores all graph edges
    private NPFDijkstraGraphInterface ae; // interface of Algo Engineer
    private DotReaderInterfaceDW reader; // interface to read from file

    /**
     * Initializes necessary interfaces for Backend implementation
     * @param ae - algorithm engineer interface with extensions of graph capabilities
     * @param reader - reader interface to read from file
     */
    public Backend(NPFDijkstraGraphInterface ae, DotReaderInterfaceDW reader) {
        this.ae = ae;
        this.reader = reader;
    }

    /**
     * load data from file about nodes and edges to construct graph
     * @param fileName - file to load data from
     * @throws FileNotFoundException if invalid file passed in
     */
    public void loadData(String fileName) throws FileNotFoundException {
        // both methods will throw exception if invalid file name passed
        nodes = reader.readNodesFromDOT(fileName);
        edges = reader.readEdgesFromDOT(fileName);
        //if (nodes == null) return;
        // inserting nodes and edges into graph
        for (String node: nodes) {
            ae.insertNode(node);
        }
        //if (edges == null) return;
        for (networkPathFinderEdgeInterfaceDW edge: edges) {
            ae.insertEdge(edge.getStart(), edge.getEnd(), edge.getWeight());
        }
    }


    /**
     * get the shortest path using Dijkstra's Algorithm between
     * a given start and end node without using disabled nodes
     * @param startNode - node which path should start from
     * @param endNode - node which path should end on
     * @param disabledNodes - nodes that path cannot go through
     * @return the shortest path between the two nodes
     */
    public List<String> getShortestPath (String startNode, String endNode, List<String> disabledNodes) {
        // utilize ae interface to call shortestPathData method for given nodes
        // throws NoSuchElementException if path not found
        if (disabledNodes != null) {
            return ae.shortestPathData(startNode, endNode, disabledNodes);
        }

        return ae.shortestPathData(startNode, endNode);
    }

    /**
     * get the shortest path cost using Dijkstra's Algorithm between
     * a given start and end node without using disabled nodes
     * @param startNode - node which path should start from
     * @param endNode - node which path should end on
     * @param disabledNodes - nodes that path cannot go through
     * @return the shortest path between the two nodes
     */
    public double getShortestPathCost(String startNode, String endNode, List<String> disabledNodes) {
        // utilize ae interface to call shortestPathCost method for given nodes
        // throws NoSuchElementException if path not found

        if (disabledNodes != null) {
            return ae.shortestPathCost(startNode, endNode, disabledNodes);
        }

        return ae.shortestPathCost(startNode, endNode);
    }


    /**
     * get all nodes/buildings in the graph
     * @return a list of all nodes in the graph
     */
    public List<String> getAllNodes() {
        // allows frontend to access all nodes in the graph
        return nodes;
    }
}