package model.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class GerenciadorEstoque {

    // Estrutura Principal: HashMap para acesso O(1) pelo ID
    private Map<String, Produto> estoquePorId;

    // Nova Estrutura Otimizada: TreeSet para manter a lista sempre ordenada pelo Nome
    private Set<Produto> estoqueOrdenadoPorNome;

    public GerenciadorEstoque() {
        // O TreeSet usará o método compareTo() da classe Produto (que ordena por nome)
        this.estoquePorId = new HashMap<>();
        this.estoqueOrdenadoPorNome = new TreeSet<>();
    }

    // --- LÓGICA DE PROGRAMAÇÃO BÁSICA (CRUD) ---
    // 1. Adicionar Produto (Atualiza HashMap e TreeSet)
    public boolean adicionarProduto(Produto produto) {
        if (estoquePorId.containsKey(produto.getIdProduto())) {
            System.out.println("ERRO: Produto com ID " + produto.getIdProduto() + " já existe no estoque.");
            return false;
        }

        // Lógica de Consistência: Adiciona em ambas as estruturas
        estoquePorId.put(produto.getIdProduto(), produto);
        estoqueOrdenadoPorNome.add(produto); // O(log N) para manter a ordenação

        System.out.println("Produto " + produto.getNome() + " adicionado com sucesso.");
        return true;
    }

    // 2. Consultar Produto (Acesso O(1) via HashMap)
    public Produto consultarProduto(String id) {
        Produto produto = estoquePorId.get(id);
        if (produto == null) {
            System.out.println("ERRO: Produto com ID " + id + " não encontrado.");
        }
        return produto;
    }

    // 3. Remover Produto (Remove de HashMap e TreeSet)
    public boolean removerProduto(String id) {
        Produto produtoRemovido = estoquePorId.remove(id);

        if (produtoRemovido != null) {
            // Lógica de Consistência: Remove da estrutura ordenada também
            estoqueOrdenadoPorNome.remove(produtoRemovido); // O(log N)
            System.out.println("Produto com ID " + id + " removido.");
            return true;
        }
        System.out.println("ERRO: Produto com ID " + id + " não encontrado para remoção.");
        return false;
    }

    // 4. Atualizar Estoque (Apenas modifica o objeto, que é referenciado nas duas estruturas)
    public boolean atualizarEstoque(String id, int deltaQuantidade) {
        Produto produto = consultarProduto(id);
        if (produto == null) {
            return false;
        }

        int novaQuantidade = produto.getQuantidadeEstoque() + deltaQuantidade;

        if (novaQuantidade < 0) {
            System.out.println("ERRO: Tentativa de deixar o estoque de " + produto.getNome() + " negativo. Operação cancelada.");
            return false;
        }

        // A modificação no objeto 'produto' se reflete automaticamente
        // em ambas as estruturas (HashMap e TreeSet) porque elas armazenam a REFERÊNCIA ao objeto.
        produto.setQuantidadeEstoque(novaQuantidade);
        System.out.println("Estoque de " + produto.getNome() + " atualizado para " + novaQuantidade + " unidades.");
        return true;
    }

    // --- LÓGICA DE RELATÓRIOS OTIMIZADA ---
    /**
     * Gera um relatório de todos os produtos ordenados pelo nome. Agora,
     * simplesmente retorna os dados do TreeSet.
     *
     * @return Uma Listagem que já está garantidamente ordenada.
     */
    public List<Produto> listarProdutosOrdenadosPorNome() {
        // A iteração sobre o TreeSet já é em ordem crescente (O(N)), 
        // mas a ordenação foi mantida em tempo real nas inserções/remoções.
        return new ArrayList<>(estoqueOrdenadoPorNome);
    }

    /**
     * Identifica e prioriza os produtos com estoque abaixo do mínimo. Usa a
     * Fila de Prioridade (PriorityQueue) para extrair o item mais crítico.
     */
    public PriorityQueue<Produto> verificarEstoqueCritico() {
        // ... (Esta lógica permanece a mesma, usando o HashMap para a varredura)
        PriorityQueue<Produto> criticos = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(p1.getQuantidadeEstoque(), p2.getQuantidadeEstoque())
        );

        for (Produto p : estoquePorId.values()) {
            if (p.getQuantidadeEstoque() <= p.getEstoqueMinimo()) {
                criticos.add(p);
            }
        }
        return criticos;
    }
}
