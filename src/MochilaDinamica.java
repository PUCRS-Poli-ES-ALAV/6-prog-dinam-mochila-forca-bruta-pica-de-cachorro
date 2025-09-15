public class MochilaDinamica {
    private static int iteracoes = 0;

    public static void main(String[] args) {
        // Caso de teste da foto
        Item[] itens = new Item[]{
            new Item(5, 2),  // peso 5kg, valor $2
            new Item(2, 4),  // peso 2kg, valor $4
            new Item(2, 2),  // peso 2kg, valor $2
            new Item(1, 3)   // peso 1kg, valor $3
        };
        
        int capacidade = 7;  // mochila de 7kg
        
        long inicio = System.currentTimeMillis();
        int resultado = resolverMochila(itens, capacidade);
        long fim = System.currentTimeMillis();
        
        System.out.println("Caso de teste da foto:");
        System.out.println("Capacidade da mochila: " + capacidade + "kg");
        System.out.println("Valor máximo: $" + resultado);
        System.out.println("Número de iterações: " + iteracoes);
        System.out.println("Tempo de execução: " + (fim - inicio) + "ms");
        
        // Mostrar a tabela de programação dinâmica
        mostrarSolucao(itens, capacidade);
    }

    public static int resolverMochila(Item[] itens, int capacidade) {
        iteracoes = 0;
        int n = itens.length;
        int[][] maxTab = new int[n + 1][capacidade + 1];

        // Inicializa primeira linha e coluna com 0
        for (int j = 0; j <= capacidade; j++) {
            maxTab[0][j] = 0;
            iteracoes++;
        }
        
        // Preenche a tabela
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= capacidade; j++) {
                iteracoes++;
                if (itens[i-1].getPeso() <= j) {
                    maxTab[i][j] = Math.max(
                        maxTab[i-1][j],
                        itens[i-1].getValor() + maxTab[i-1][j - itens[i-1].getPeso()]
                    );
                } else {
                    maxTab[i][j] = maxTab[i-1][j];
                }
            }
        }
        
        return maxTab[n][capacidade];
    }

    private static void mostrarSolucao(Item[] itens, int capacidade) {
        int n = itens.length;
        int[][] maxTab = new int[n + 1][capacidade + 1];
        
        // Reconstrói a tabela
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= capacidade; j++) {
                if (itens[i-1].getPeso() <= j) {
                    maxTab[i][j] = Math.max(
                        maxTab[i-1][j],
                        itens[i-1].getValor() + maxTab[i-1][j - itens[i-1].getPeso()]
                    );
                } else {
                    maxTab[i][j] = maxTab[i-1][j];
                }
            }
        }
        
        // Mostra a tabela
        System.out.println("\nTabela de programação dinâmica:");
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= capacidade; j++) {
                System.out.printf("%4d", maxTab[i][j]);
            }
            System.out.println();
        }
        
        // Encontra os itens selecionados
        System.out.println("\nItens selecionados:");
        int i = n;
        int j = capacidade;
        while (i > 0 && j > 0) {
            if (maxTab[i][j] != maxTab[i-1][j]) {
                System.out.println("Item " + i + ": (peso=" + itens[i-1].getPeso() + 
                                 ", valor=" + itens[i-1].getValor() + ")");
                j = j - itens[i-1].getPeso();
            }
            i--;
        }
    }
}
