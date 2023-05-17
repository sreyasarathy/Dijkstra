// --== CS400 Role Code Header ==--
// Name: Sreya Sarathy
// CSL Username: ssarathy
// Email: sarathy2@wisc.edu
// Team: DT Blue
// Lecture #: MWF Lecture 1 3:30 PM with Prof Florian
// Notes to Grader: <any optional extra notes to your grader>

/**
 * An interface representing an edge in a network path finder graph.
 * @author sreyasarathy
 */
public interface networkPathFinderEdgeInterfaceDW {
    // public networkPathFinderEdgeInterfaceDW(String start, String node, int weight)
    public String getStart();
    public String getEnd();
    public int getWeight();
}
