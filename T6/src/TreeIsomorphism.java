
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

    // • 1. isTree(): V>0 + E==V-1 + CONEXO (DFS)
    public boolean isTree() {
        int V = graph.V();
        int E = graph.E();
        if (V <= 0 || E != V - 1) return false;

        boolean[] visited = new boolean[V];
        dfs(0, visited);  // Marca alcançáveis

        // Checa todos visitados
        for (boolean b : visited) {
            if (!b) throw new UnsupportedOperationException("O DFS não visitou todas as arestas");
        }
        return true;
    }

    // • 2. getValidationMessage(): Detalhes erro (E ou desconexo)
    public String getValidationMessage() {
        try{
            if (isTree()){
                return  "É uma árvore válida.";
            }
            else return "Não é umma árvore válida: Não cumpre a validação E = V - 1";
        }
        catch (Exception e){return e.getMessage();}

    }

    private void dfs(int s, boolean[] visited) {// DFS iterativo
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        visited[s] = true;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            for (int w : graph.adj(v)) {
                if (!visited[w]) {
                    visited[w] = true;
                    stack.push(w);
                }
            }
        }
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
    /**
     * Gera a codificação canônica da subárvore enraizada no nó 'node'.
     *
     * @param node: vértice atual (raiz da subárvore sendo codificada).
     *              Para a raiz principal, chame com centers[0] e parent=-1.
     * @param parent: vértice pai da chamada anterior (evita recursão infinita em backtrack).
     *                Use -1 para a raiz (sentinela inválida, pois vértices são 0..V-1).
     *                No if(child != parent), filtra o pai para processar só filhos descendentes.
     */
    // • 4. getCanonicalEncoding(node, parent): RECURSIVO
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
