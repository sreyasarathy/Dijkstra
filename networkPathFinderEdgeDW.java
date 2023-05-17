// --== CS400 Role Code Header ==--
// Name: Sreya Sarathy
// CSL Username: ssarathy
// Email: sarathy2@wisc.edu
// Team: DT Blue
// Lecture #: MWF Lecture 1 3:30 PM with Prof Florian
// Notes to Grader: <any optional extra notes to your grader>


/**
 * This class implements the networkPathFinderEdgeInterfaceDW interface to represent
 * an edge in a network path finder algorithm. Each edge has a starting node,
 * an ending node, and a weight. The class provides methods to get the starting node,
 * the ending node, and the weight of the edge.
 *
 * @author sreyasarathy
 */
public class networkPathFinderEdgeDW implements networkPathFinderEdgeInterfaceDW {
    private String start; // Starting node of the edge
    private String end; // Ending node of the edge
    private int weight; // Weight of the edge


    /**
     * Constructor for networkPathFinderEdge object
     * @param start  - starting node
     * @param end    - ending node
     * @param weight - weight of the edge
     */
    public networkPathFinderEdgeDW(String start, String end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }


    /**
     * The following method returns the starting node of the edge as a string.
     *
     * @return start - the starting point
     */
    @Override
    public String getStart() {
        return start;
    }


    /**
     * The following method returns the ending node of the edge as a string.
     *
     * @return end - the ending point
     */
    @Override
    public String getEnd() {
        return end;
    }


    /**
     * The following method returns the weight of the edge as an integer.
     *
     * @return weight - the weight of the edge
     */
    @Override
    public int getWeight() {
        return weight;
    }

}
