import java.util.List;

/**interface AEInterface<NodeType, EdgeType extends Number> extends GraphADT<NodeType, EdgeType> {
 public SearchNode findPathWithoutDisabledNodes(String startNode, String endNode, List<String> disabledNodes);
 public List<String> shortestPathData(SearchNode path);
 public double shortestPathCost(SearchNode path);
 }**/


interface AEInterface<SearchNode> extends GraphADT<String,Integer> {
    public SearchNode findPathWithoutDisabledNodes(String startNode, String endNode, List<String> disabledNodes);
    public List<String> shortestPathData(SearchNode path);
    public double shortestPathCost(SearchNode path);
}

