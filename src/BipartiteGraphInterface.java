import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface BipartiteGraphInterface<Vertex> {
    /**
     *  Returns a collection of objects representing the vertices of the graph.
     */
    Collection<Vertex> getVertices();

    /**
     *  Returns the number of edges present in the graph.
     */
    int getNumberOfEdges();

    /**
     *  Returns the number of vertices present in the graph.
     */
    int getNumberOfVertices();

    /**
     *  Returns whether vertex u and vertex v are neighbours.
     */
    boolean areNeighbors(Vertex u, Vertex v);

    /**
     *  Returns the degree of vertex u.
     */
    int getDegree(Vertex u);

    /**
     *  Returns a collection of objects representing the neighbours of vertex u.
     */
    Collection<Vertex> getNeighborsOf(Vertex u);

    /**
     *  Apply the chosen metaheuristic for a maximum of maxIterations iterations. This method returns a Map Entry
     *  containing a Long representing the number of crossings for the acquired solution as well as a List of
     *  Vertices representing the order of the vertices in the acquired solution.
     */
    Map.Entry<Long,List<Vertex>> runMetaheuristicTest(int maxIterations);


    /**
     *  Apply the first chosen heuristic. This method returns a Map Entry containing a Long representing the number
     *  of crossings for the acquired solution as well as a List of Vertices representing the order of the vertices in
     *  the acquired solution.
     */
    Map.Entry<Long,List<Vertex>> runFirstHeuristic();


    /**
     *  Apply the second chosen heuristic. This method returns a Map Entry containing a Long representing the number
     *  of crossings for the acquired solution as well as a List of Vertices representing the order of the vertices in
     *  the acquired solution.
     */
    Map.Entry<Long,List<Vertex>> runSecondHeuristic();

}
