// --== CS400 Role Code Header ==--
// Name: Sreya Sarathy
// CSL Username: ssarathy
// Email: sarathy2@wisc.edu
// Team: DT Blue
// Lecture #: MWF Lecture 1 3:30 PM with Prof Florian
// Notes to Grader: <any optional extra notes to your grader>


import java.io.FileNotFoundException;
import java.util.List;

/**
 * This interface defines the methods for reading nodes and edges from a DOT file.
 * readNodesFromDot - Reads the nodes from a DOT file and returns them as a list of strings.
 * readEdgesFromDot - Reads the edges from a DOT file and returns them as a list of
 * networkPathFinderEdgeInterfaceDW objects.
 * @author sreyasarathy
 */

public interface DotReaderInterfaceDW {
    // public dotReaderInterface();
    public List<String> readNodesFromDOT(String filename) throws FileNotFoundException;
    public List<networkPathFinderEdgeInterfaceDW> readEdgesFromDOT(String filename) throws FileNotFoundException;

}
