// --== CS400 File Header Information ==--
// Name: Naman Parekh
// Email: ncparekh@wisc.edu
// Group and Team: DT
// Group TA: Daniel Finer
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.*;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes.  This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType,EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph.  The final node in this path is stored in its node
     * field.  The total cost of this path is stored in its cost field.  And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations.  The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {



        if (!containsNode(start) || !containsNode(end)) {
            throw new NoSuchElementException("Error: Start or end data do not correspond to" +
                    " graph node");
        }

        SearchNode searchPath;
        // hashtable that stores nodes which have already been visited
        Hashtable<Node, SearchNode> visitHash = new Hashtable<>();
        // creating a priority queue for nodes
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();

        double totalCost = 0.0;
        // inserting starting node
        pq.add(new SearchNode(nodes.get(start), totalCost, null));

        while (!pq.isEmpty()) {
            searchPath = pq.poll(); // removes smallest 'weight' element from queue
            if (!visitHash.containsKey(searchPath.node)) {
                visitHash.put(searchPath.node, searchPath); // marks node as visited
                totalCost = searchPath.cost;
                if (searchPath.node.data.equals(end)) {
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


    /*

        // Checks to see if the start and end nodes exist in our graph
        Node startNode = nodes.get(start);
        Node endNode = nodes.get(end);

        if (startNode == null || endNode == null) {
            throw new NoSuchElementException("Error: either the Start or End node cannot be found in this graph.");
        }

        // Initializes the priority queue and visited hash table used by the algorithm
        PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();
        Hashtable<Node, SearchNode> visitedNodes = new Hashtable<>();

        // Creates a search node to represent the start node and adds it to the priority queue
        SearchNode startingSearchNode = new SearchNode(startNode, 0, null);
        priorityQueue.offer(startingSearchNode);

        // Traverses the graph using Dijkstra's algo while priority queue is not empty
        while (!priorityQueue.isEmpty()) {

            // Finds and removes the search node with the lowest cost from the priority queue
            SearchNode currentSearchNode = priorityQueue.poll();
            Node currentNode = currentSearchNode.node;

            // Checks to see if we have reached the destination node
            if (currentNode.equals(endNode)) {
                return currentSearchNode;
            }

            // Checks to see if we have previously visited this node
            if (visitedNodes.containsKey(currentNode)) {
                continue;
            }

            // Marks this node as now visited
            visitedNodes.put(currentNode, currentSearchNode);

            // Analyzes all the neighboring nodes of the current node
            for (Edge edge : currentNode.edgesLeaving) {
                Node neighborNode = edge.successor;
                double neighborCost = currentSearchNode.cost + edge.data.doubleValue();
                SearchNode neighborSearchNode =
                        new SearchNode(neighborNode, neighborCost, currentSearchNode);

                // Adds the neighbor node to the queue if it has not been previously visited
                if (!visitedNodes.containsKey(neighborNode)) {
                    priorityQueue.offer(neighborSearchNode);
                }
            }
        }
        // There is no path from the given start to end
        throw new NoSuchElementException("Error: no possible path from start to end found in this graph.");
    }

     */


    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value.  This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shortest path.  This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */


    public List<NodeType> shortestPathData(NodeType start, NodeType end) {

        // creates a list to store data items from node
        List<NodeType> list = new ArrayList<>();

        // adds all the data computed by the nodes visited in the shortest path
        SearchNode sc = computeShortestPath(start, end);
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
        List<NodeType> actualList = new ArrayList<>(list);
        Collections.reverse(actualList);

        return actualList;

    }


    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path from the node containing the start data to the node containing the
     * end data.  This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {

        // calls the recursive method to find total cost
        SearchNode sc = computeShortestPath(start, end);

        return sc.cost;
    }

    /**
     * Tester that checks if the computeShortestPath() method works correctly. Additionally,
     * checks that shortestPathCost() and shortestPathData() methods return the correct output
     * based on the graph example covered in lecture
     *
     */
    @Test
    void Test1() {

        DijkstraGraph<Character, Integer> graph = new DijkstraGraph<>();

        // inserting nodes into graph
        graph.insertNode('A');
        graph.insertNode('B');
        graph.insertNode('C');
        graph.insertNode('D');
        graph.insertNode('E');
        graph.insertNode('F');
        graph.insertNode('G');
        graph.insertNode('H');

        // inserting edges with weights into graph
        graph.insertEdge('A', 'B', 4);
        graph.insertEdge('A', 'C', 2);
        graph.insertEdge('A', 'E', 15);
        graph.insertEdge('B', 'E', 10);
        graph.insertEdge('B', 'D', 1);
        graph.insertEdge('D', 'E', 3);
        graph.insertEdge('C', 'D', 5);
        graph.insertEdge('D', 'F', 0);
        graph.insertEdge('F', 'D', 2);
        graph.insertEdge('F', 'H', 4);
        graph.insertEdge('G', 'H', 4);

        // calls Dijkstra's Algorithm and checks if SearchNode node's data is computed correctly

        // Example 1 in Lecture:
        assertEquals('E', graph.computeShortestPath('A', 'E').node.data);

        // Example 2 in Lecture:
        assertEquals('F', graph.computeShortestPath('A', 'F').node.data);


        // Example 3 in Lecture: (Should throw a NullPointerException)
        assertThrows(NoSuchElementException.class, () -> {
            graph.computeShortestPath('A', 'G');
        });

        // checks cost of shortest path between A to E (Lecture Example 1)
        assertEquals(8, graph.shortestPathCost('A', 'E'));

        // checks cost of shortest path between A to F (Lecture Example 2)
        assertEquals(5, graph.shortestPathCost('A', 'F'));

        // checks sequence of data along the shortest path between A to E (Lecture Example 1)
        assertEquals("[A, B, D, E]", graph.shortestPathData('A', 'E').toString());

        // checks sequence of data along the shortest path between A to F (Lecture Example 2)
        assertEquals("[A, B, D, F]", graph.shortestPathData('A', 'F').toString());

    }

    /**
     * Tester that checks if the shortestPathCost() and shortestPathData() methods return
     * the correct output based on the graph example covered in lecture, however, using different
     * start and end nodes as covered in lecture
     *
     */
    @Test
    void Test2() {

        DijkstraGraph<Character, Integer> graph = new DijkstraGraph<>();

        // inserting nodes into graph
        graph.insertNode('A');
        graph.insertNode('B');
        graph.insertNode('C');
        graph.insertNode('D');
        graph.insertNode('E');
        graph.insertNode('F');
        graph.insertNode('G');
        graph.insertNode('H');

        // inserting edges with weights into graph
        graph.insertEdge('A', 'B', 4);
        graph.insertEdge('A', 'C', 2);
        graph.insertEdge('A', 'E', 15);
        graph.insertEdge('B', 'E', 10);
        graph.insertEdge('B', 'D', 1);
        graph.insertEdge('D', 'E', 3);
        graph.insertEdge('C', 'D', 5);
        graph.insertEdge('D', 'F', 0);
        graph.insertEdge('F', 'D', 2);
        graph.insertEdge('F', 'H', 4);
        graph.insertEdge('G', 'H', 4);

        // checks cost of shortest path between A to D
        assertEquals(5, graph.shortestPathCost('A', 'D'));

        // checks cost of shortest path between F to E
        assertEquals(5, graph.shortestPathCost('F', 'E'));

        // checks sequence of data along the shortest path between A to D
        assertEquals("[A, B, D]", graph.shortestPathData('A', 'D').toString());

        // checks sequence of data along the shortest path between A to F
        assertEquals("[F, D, E]", graph.shortestPathData('F', 'E').toString());

    }

    /**
     * Tester that checks if the shortestPathCost() and shortestPathData() methods throw respective
     * exceptions when asked to search for the shortest path between two nodes in a graph that have
     * no sequence of directed edges connecting them from start to end node.
     *
     */
    @Test
    void Test3() {

        DijkstraGraph<Character, Integer> graph = new DijkstraGraph<>();

        // inserting nodes into graph
        graph.insertNode('A');
        graph.insertNode('B');
        graph.insertNode('C');
        graph.insertNode('D');
        graph.insertNode('E');
        graph.insertNode('F');
        graph.insertNode('G');
        graph.insertNode('H');

        // inserting edges with weights into graph
        graph.insertEdge('A', 'B', 4);
        graph.insertEdge('A', 'C', 2);
        graph.insertEdge('A', 'E', 15);
        graph.insertEdge('B', 'E', 10);
        graph.insertEdge('B', 'D', 1);
        graph.insertEdge('D', 'E', 3);
        graph.insertEdge('C', 'D', 5);
        graph.insertEdge('D', 'F', 0);
        graph.insertEdge('F', 'D', 2);
        graph.insertEdge('F', 'H', 4);
        graph.insertEdge('G', 'H', 4);

        // Note: We will be testing sequence of directed edges between H to G, E to C

        // checks sequence of data along the shortest path between H to G
        assertThrows(NoSuchElementException.class, () -> {
            graph.shortestPathData('H', 'G');
        });

        // checks sequence of data along the shortest path between E to C
        assertThrows(NoSuchElementException.class, () -> {
            graph.shortestPathData('E', 'C');
        });
    }
}
