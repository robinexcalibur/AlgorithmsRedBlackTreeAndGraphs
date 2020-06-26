import java.util.ArrayList;

public class ListGraph {
    //an undirected graph using adjacency lists, and uses Users

    private final int v; //number of verticies
    private int e; //number of edges

    private ArrayList<Integer>[] adjList;

    public ListGraph(int v) {
        this.v = v;
        this.e = 0;

        adjList = new ArrayList[v];
        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<Integer>(); //makes an arrayList at each vertex
        }
    }

    //adds edge to the graph
    public void addEdge(int v, int w) {
        adjList[v].add(w);
        adjList[w].add(v);
        e++;
    }

    public void removeEdge(int v, int w) {
        if(hasEdge(v,w)) {
            adjList[v].remove(w);
            adjList[w].remove(v);
            e--;
        }
    }

    public boolean hasEdge(int v, int w) {
        return adjList[v].indexOf(w) != -1;
    }

    public int e() {
        return e;
    }

    public int v() {
        return v;
    }

    public ArrayList<Integer> getEdges(int v) {
        return adjList[v];
    }

    public int degree(int v) {
        return adjList[v].size();
    }

}
