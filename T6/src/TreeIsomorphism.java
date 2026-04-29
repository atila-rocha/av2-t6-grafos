
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeIsomorphism {
    private final Graph graph;

    public TreeIsomorphism(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph nao pode ser nulo");
        }
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public boolean isTree() {
        int V = graph.V();
        int E = graph.E();
        return V > 0 && E == V - 1;
        /*
        * TODO: Adicionar DFS/BFS para validar a conexidade do grafo
        *  Motivo: Pode ser que no dataset tenha duas arvores e que E = V - 1
        * */
    }

    public String getValidationMessage() {
        if (isTree()){
            return  "É uma árvore válida.";
        }
        else return "Não é umma árvore válida: Não cumpre a validação E = V - 1";
    }

    public int[] getCenters() {
        int n = graph.V();
        if (n == 0) return new int[0];

        int[] degree = new int[n];
        List<Integer> leaves = new ArrayList<>();

        // Inicial: graus e folhas
        for (int i = 0; i < n; i++) {
            degree[i] = graph.degree(i);
            if (degree[i] <= 1) {
                leaves.add(i);
                degree[i] = 0;
            }
        }

        int processedLeafs = leaves.size();

        while (processedLeafs < n) {
            List<Integer> newLeaves = new ArrayList<>();
            for (int node : leaves) {  // Foreach limpo!
                for (int neighbor : graph.adj(node)) {
                    if (--degree[neighbor] == 1) {
                        newLeaves.add(neighbor);
                    }
                }
                degree[node] = 0;
            }
            processedLeafs += newLeaves.size();
            leaves = newLeaves;
        }

        // leaves = centros
        // return leaves.stream().mapToInt(Integer::intValue).toArray();
        return leaves.stream().mapToInt(Integer::intValue).toArray();
    }

    /*public String getCanonicalEncoding() {
        if (!isTree()) {
            throw new IllegalStateException("Apenas para árvores válidas");
        }
        int[] centers = getCenters();
        if (centers.length == 0) return "()";  // Trivial
        int root = centers[0];  // Enraiza no primeiro centro
        return encode(root, -1);
    }*/

    public String getCanonicalEncoding(int node, int parent) {
        List<String> labels = new ArrayList<>();
        for (int child : graph.adj(node)) {
            if (child != parent) {
                labels.add(getCanonicalEncoding(child, node));
            }
        }
        Collections.sort(labels);  // Lex ordem (Fiset)
        StringBuilder sb = new StringBuilder("(");
        for (String label : labels) {
            sb.append(label);
        }
        return sb.append(")").toString();
    }
}
