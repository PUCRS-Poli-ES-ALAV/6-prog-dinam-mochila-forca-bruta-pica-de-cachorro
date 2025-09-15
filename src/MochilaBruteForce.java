import java.util.ArrayList;
import java.util.List;

public class MochilaBruteForce {
    private static int iteracoes = 0;

    public static void main(String[] args) {
        // Exemplo de teste
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(2, 3));  // peso 2, valor 3
        itens.add(new Item(3, 4));  // peso 3, valor 4
        itens.add(new Item(4, 5));  // peso 4, valor 5
        itens.add(new Item(5, 6));  // peso 5, valor 6

        int capacidade = 10;
        
        long inicio = System.currentTimeMillis();
        ResultadoMochila resultado = resolverMochila(itens, capacidade);
        long fim = System.currentTimeMillis();

        System.out.println("Valor máximo: " + resultado.getValorMaximo());
        System.out.println("Itens selecionados: " + resultado.getItensSelecionados());
        System.out.println("Número de iterações: " + iteracoes);
        System.out.println("Tempo de execução: " + (fim - inicio) + "ms");
    }

    public static ResultadoMochila resolverMochila(List<Item> itens, int capacidade) {
        iteracoes = 0;
        int n = itens.size();
        int valorMaximo = 0;
        List<Integer> melhorCombinacao = new ArrayList<>();
        
        // Gerar todas as combinações possíveis (2^n)
        for (int i = 0; i < (1 << n); i++) {
            iteracoes++;
            int pesoAtual = 0;
            int valorAtual = 0;
            List<Integer> combinacaoAtual = new ArrayList<>();
            
            // Verificar cada bit
            for (int j = 0; j < n; j++) {
                iteracoes++;
                if ((i & (1 << j)) > 0) {
                    pesoAtual += itens.get(j).getPeso();
                    valorAtual += itens.get(j).getValor();
                    combinacaoAtual.add(j);
                }
            }
            
            // Atualizar melhor solução se necessário
            if (pesoAtual <= capacidade && valorAtual > valorMaximo) {
                valorMaximo = valorAtual;
                melhorCombinacao = new ArrayList<>(combinacaoAtual);
            }
        }
        
        return new ResultadoMochila(valorMaximo, melhorCombinacao);
    }
}

class ResultadoMochila {
    private int valorMaximo;
    private List<Integer> itensSelecionados;

    public ResultadoMochila(int valorMaximo, List<Integer> itensSelecionados) {
        this.valorMaximo = valorMaximo;
        this.itensSelecionados = itensSelecionados;
    }

    public int getValorMaximo() {
        return valorMaximo;
    }

    public List<Integer> getItensSelecionados() {
        return itensSelecionados;
    }
}
