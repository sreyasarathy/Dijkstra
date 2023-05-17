import java.util.List;

public interface NPFDijkstraGraphInterface extends GraphADT<String, Integer> {

    public List<String> shortestPathData(String startNode, String endNode, List<String> disabledNodes);

    public double shortestPathCost(String startNode, String endNode, List<String> disabledNodes);

}