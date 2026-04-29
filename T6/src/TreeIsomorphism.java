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
        throw new UnsupportedOperationException("TODO: encontrar o(s) centro(s) da arvore");
    }

    public String getCanonicalEncoding() {
        throw new UnsupportedOperationException("TODO: gerar a codificacao canonica da arvore");
    }
}
