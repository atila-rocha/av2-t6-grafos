public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "informe dois arquivos de entrada. Ex.: java Main ../dados/arvore1.txt ../dados/arvore2.txt"
            );
        }

        Graph tree1 = new Graph(new In(args[0]));
        Graph tree2 = new Graph(new In(args[1]));

        StdOut.println("Lista de adjacência da árvore 1:");
        StdOut.println(tree1);
        StdOut.println();

        StdOut.println("Lista de adjacência da árvore 2:");
        StdOut.println(tree2);
        StdOut.println();

        TreeIsomorphism analysis1 = new TreeIsomorphism(tree1);
        TreeIsomorphism analysis2 = new TreeIsomorphism(tree2);

        // Validação
        StdOut.println("Árvore 1 - " + analysis1.getValidationMessage());
        StdOut.println("Árvore 2 - " + analysis2.getValidationMessage());
        StdOut.println();

        if (!analysis1.isTree() || !analysis2.isTree()) {
            StdOut.println("Veredito: Não isomorfas (árvore inválida)");
            return;
        }

        // Centros
        int[] centers1 = analysis1.getCenters();
        int[] centers2 = analysis2.getCenters();
        StdOut.println("Centros Árvore 1: " + java.util.Arrays.toString(centers1));
        StdOut.println("Centros Árvore 2: " + java.util.Arrays.toString(centers2));
        StdOut.println();

        // Codificação: tree1 no primeiro centro
        String code1 = analysis1.getCanonicalEncoding(centers1[0], -1);
        StdOut.println("Código canônico Árvore 1: " + code1);
        StdOut.println();

        // Testa centros de tree2 vs code1
        boolean isomorfas = false;
        for (int center : centers2) {
            String code2 = analysis2.getCanonicalEncoding(center, -1);
            StdOut.println("Código canônico Árvore 2 (centro " + center + "): " + code2);
            if (code1.equals(code2)) {
                isomorfas = true;
                break;
            }
        }

        StdOut.println("Veredito: " + (isomorfas ? "Isomorfas" : "Não isomorfas"));
    }
}