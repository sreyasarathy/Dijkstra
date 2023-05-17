// --== CS400 File Header Information ==--
// Name: Naman Parekh
// Email: ncparekh@wisc.edu
// Group and Team: DT
// Group TA: Daniel Finer
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.*;

/**
 * This class extends the DijkstraGraph data structure to provide our respective application with
 * more comprehensive abilities or tasks the user can do if wishes. This class makes use of the
 * Dijkstra Algorithm.
 */
public class NPFDijkstraGraph extends DijkstraGraph<String, Integer> implements NPFDijkstraGraphInterface {

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations while taking into
     * account of any nodes the user wants to disable (i.e. any nodes the user doesn't
     * want their shortest path calculation to go through)
     *
     * @param startNode the data item in the starting node for the path
     * @param endNode the data item in the destination node for the path
     * @param disabledNodes a list of String objects that store the name of the nodes
     *                      the user wants to disable
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
    protected SearchNode findPathWithDisabledNodes
    (String startNode, String endNode, List<String> disabledNodes) {

        if (!containsNode(startNode) || !containsNode(endNode)) {
            throw new NoSuchElementException("Error: Start or end data do not correspond to" +
                    "graph node");
        }

        if (disabledNodes.contains(startNode) || disabledNodes.contains(endNode)) {
            throw new NoSuchElementException("Error: Start/End data cannot be contained in" +
                    "list of nodes you want to disable");
        }

        SearchNode searchPath;
        // hashtable that stores nodes which have already been visited
        Hashtable<Node, SearchNode> visitHash = new Hashtable<>();
        // creating a priority queue for nodes
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();
        double totalCost = 0.0; // variable that stores path's total cost from startNode

        // inserting starting node
        pq.add(new SearchNode(nodes.get(startNode), totalCost, null));


        while (!pq.isEmpty()) {
            searchPath = pq.poll(); // removes smallest 'weight' element from queue
            // if a node from list disabledNodes is encountered, add it to Hashtable,
            // so it is not visited
            if (disabledNodes.contains(searchPath.node.data)) {
                visitHash.put(searchPath.node, searchPath);
            }
            if (!visitHash.containsKey(searchPath.node)) {
                visitHash.put(searchPath.node, searchPath); // marks node as visited
                totalCost = searchPath.cost;
                if (searchPath.node.data.equals(endNode)) {
                    return searchPath; // if shortest path computed, return
                }
                // adds unvisited nodes to the queue from the searchPath's adjacent nodes
                for (Edge edge : searchPath.node.edgesLeaving) {
                    if (!visitHash.containsKey(edge.successor)) {
                        pq.add(new SearchNode(edge.successor, totalCost + edge.data.doubleValue(),
                                searchPath));
                    }
                }
            }
        }

        throw new NoSuchElementException("Error: No path found");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value while taking into account of any nodes the user wants to
     * disable (i.e. any nodes the user doesn't want their shortest path calculation
     * to go through)
     *
     * @param startNode the data item in the starting node for the path
     * @param endNode the data item in the destination node for the path
     * @param disabledNodes a list of String objects that store the name of the nodes
     *                      the user wants to disable
     * @return list of data item from node along this shortest path
     */
    @Override
    public List<String> shortestPathData(String startNode, String endNode, List<String> disabledNodes) {

        // creates a list to store data items from node
        List<String> list = new ArrayList<>();

        // adds all the data computed by the nodes visited in the shortest path
        SearchNode sc = findPathWithDisabledNodes(startNode, endNode, disabledNodes);

        while (sc != null) {
            if (sc.node != null) {
                list.add(sc.node.data);
            }
            if (sc.predecessor == null) {
                break;
            }
            sc = sc.predecessor;
        }

        // reverses the order of elements in the list to make the list: start to end value
        List<String> actualList = new ArrayList<>(list);
        Collections.reverse(actualList);

        return actualList;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path from the node containing the start data to the node containing the
     * end data while taking into account of any nodes the user wants to
     * disable (i.e. any nodes the user doesn't want their shortest path calculation
     * to go through)
     *
     * @param startNode the data item in the starting node for the path
     * @param endNode the data item in the destination node for the path
     * @param disabledNodes a list of String objects that store the name of the nodes
     *                      the user wants to disable
     * @return the cost of the shortest path between these nodes
     */
    @Override
    public double shortestPathCost(String startNode, String endNode, List<String> disabledNodes) {

        // calls the protected helper method to find SearchNode
        SearchNode node = findPathWithDisabledNodes(startNode, endNode, disabledNodes);

        // returns the SearchNode's cost (total path cost)
        return node.cost;
    }

}
