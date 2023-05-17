import java.io.FileNotFoundException;
import java.io.StringBufferInputStream;
import java.util.List;

/**
 * Interface for backend to pass data between frontend developer and algorithm engineer
 * Frontend will call appropriate methods from backend to interact with data and graph correctly
 */
interface BackendInterface {

    /**
     * load data from file about different locations to construct graph
     * @param fileName - file to load data from
     * @throws FileNotFoundException if invalid file passed in
     */
    public void loadData(String fileName) throws FileNotFoundException;

    /**
     * get the shortest path using Dijkstra's Algorithm between
     * a given start and end node without using disabled nodes
     * @param startNode - node which path should start from
     * @param endNode - node which path should end on
     * @param disabledNodes - nodes that path cannot go through
     * @return the shortest path between the two nodes
     */
    public List<String> getShortestPath(String startNode, String endNode, List<String> disabledNodes);

    /**
     * get the shortest path cost using Dijkstra's Algorithm between
     * a given start and end node without using disabled nodes
     * @param startNode - node which path should start from
     * @param endNode - node which path should end on
     * @param disabledNodes - nodes that path cannot go through
     * @return the shortest path between the two nodes
     */
    public double getShortestPathCost(String startNode, String endNode, List<String> disabledNodes);



    /**
     * get all nodes/buildings in the graph
     * @return a list of all nodes in the graph
     */
    public List<String> getAllNodes();



}
