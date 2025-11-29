package main;

import java.util.Scanner;
import model.entities.GerenciadorEstoque;
import java.util.PriorityQueue;
import model.entities.Produto;

public class Main {

    private static GerenciadorEstoque gerenciador = new GerenciadorEstoque();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicialização de alguns dados para teste imediato
        inicializarDadosDeTeste();

        int opcao;
        do {
            exibirMenu();
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha
                processarOpcao(opcao);
            } else {
                System.out.println("\n❌ Opção inválida. Digite um número de 0 a 6.");
                scanner.nextLine(); // Consumir entrada inválida
                opcao = -1;
            }
        } while (opcao != 0);

        System.out.println("Obrigado por usar o Sistema de Gerenciamento de Estoque. Encerrando...");
    }

    private static void exibirMenu() {
        System.out.println("\n==============================================");
        System.out.println("        SISTEMA DE GESTÃO DE ESTOQUE");
        System.out.println("         (Lógica e Estrutura de Dados)");
        System.out.println("==============================================");
        System.out.println("1. Adicionar Novo Produto");
        System.out.println("2. Consultar Produto (Acesso O(1))");
        System.out.println("3. Atualizar Estoque (Entrada/Saída)");
        System.out.println("4. Remover Produto");
        System.out.println("----------------------------------------------");
        System.out.println("5. Relatório Ordenado (Acesso O(log N) via TreeSet)");
        System.out.println("6. Verificar Estoque Crítico (Priority Queue)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarProduto();
                break;
            case 2:
                consultarProduto();
                break;
            case 3:
                atualizarEstoque();
                break;
            case 4:
                removerProduto();
                break;
            case 5:
                listarOrdenado();
                break;
            case 6:
                verificarCritico();
                break;
            case 0:
                break;
            default:
                System.out.println("❌ Opção inválida.");
        }
    }

    // --- FUNÇÕES DE LÓGICA DO CONSOLE ---
    private static void inicializarDadosDeTeste() {
        System.out.println("Iniciando com dados de teste...");
        // Produtos para testar a ordem alfabética (C, A, B)
        gerenciador.adicionarProduto(new Produto("P003", "Cabo HDMI Premium", 100, 25.00, 50));
        gerenciador.adicionarProduto(new Produto("P001", "Adaptador USB-C", 10, 50.00, 20)); // Estoque Crítico
        gerenciador.adicionarProduto(new Produto("P002", "Bateria Portátil 20000mAh", 5, 150.00, 10)); // Muito Crítico
        System.out.println("Dados iniciais carregados.");
    }

    private static void adicionarProduto() {
        System.out.println("\n--- Adicionar Produto ---");
        System.out.print("ID do Produto (Chave O(1)): ");
        String id = scanner.nextLine().toUpperCase();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Quantidade Inicial: ");
        int qtd = scanner.nextInt();
        System.out.print("Preço Unitário: R$");
        double preco = scanner.nextDouble();
        System.out.print("Estoque Mínimo para Alerta: ");
        int min = scanner.nextInt();
        scanner.nextLine(); // Consumir linha

        Produto novo = new Produto(id, nome, qtd, preco, min);
        gerenciador.adicionarProduto(novo);
    }

    private static void consultarProduto() {
        System.out.println("\n--- Consultar Produto ---");
        System.out.print("Digite o ID do produto para consulta O(1): ");
        String id = scanner.nextLine().toUpperCase();
        Produto p = gerenciador.consultarProduto(id);
        if (p != null) {
            System.out.println("✅ Produto encontrado:");
            System.out.println(p);
        }
    }

    private static void atualizarEstoque() {
        System.out.println("\n--- Atualizar Estoque (Entrada + / Saída -) ---");
        System.out.print("ID do Produto: ");
        String id = scanner.nextLine().toUpperCase();
        System.out.print("Quantidade para Adicionar/Remover (Ex: 50 para entrada, -5 para saída): ");
        int delta = scanner.nextInt();
        scanner.nextLine();

        gerenciador.atualizarEstoque(id, delta);
    }

    private static void removerProduto() {
        System.out.println("\n--- Remover Produto ---");
        System.out.print("Digite o ID do produto a ser removido: ");
        String id = scanner.nextLine().toUpperCase();
        gerenciador.removerProduto(id);
    }

    private static void listarOrdenado() {
        System.out.println("\n--- Relatório de Estoque Ordenado por Nome (TreeSet) ---");
        // Demonstração da ordem garantida pelo TreeSet (O(log N) na inserção)
        gerenciador.listarProdutosOrdenadosPorNome().forEach(p -> System.out.println(p));
    }

    private static void verificarCritico() {
        System.out.println("\n--- Estoque Crítico (Fila de Prioridade) ---");
        // Demonstração do uso da PriorityQueue/Heap
        PriorityQueue<Produto> criticos = gerenciador.verificarEstoqueCritico();

        if (criticos.isEmpty()) {
            System.out.println("✅ Nenhum produto está abaixo do estoque mínimo. Tudo OK.");
            return;
        }

        System.out.println("❗ PRODUTOS EM ORDEM DE URGÊNCIA (Menor Estoque -> Maior Urgência):");
        while (!criticos.isEmpty()) {
            Produto proximo = criticos.poll(); // Extrai o item mais urgente (menor estoque)
            System.out.printf("  [URGENTE - %d un.] %s (Mínimo: %d)\n",
                    proximo.getQuantidadeEstoque(),
                    proximo.getNome(),
                    proximo.getEstoqueMinimo());
        }
    }
}
