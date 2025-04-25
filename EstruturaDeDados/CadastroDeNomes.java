
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CadastroDeNomes {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String ARQUIVO_JSON = "nomes.json";
    private static List<String> nomes = new ArrayList<>();

    public static void main(String[] args) {
        carregarNomes();

        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1 - Adicionar nome");
            System.out.println("2 - Listar nomes");
            System.out.println("3 - Remover nome");
            System.out.println("4 - Buscar nome");
            System.out.println("5 - Salvar nomes em arquivo JSON");
            System.out.println("6 - Carregar nomes de arquivo JSON");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> adicionarNome();
                case 2 -> listarNomes();
                case 3 -> removerNome();
                case 4 -> buscarNome();
                case 5 -> salvarNomes();
                case 6 -> carregarNomes();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void adicionarNome() {
        System.out.print("Digite o nome a ser adicionado: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome vazio não pode ser adicionado, tente novamente.");
        } else if (nomes.contains(nome)) {
            System.out.println("Este Nome já existe na lista.");
        } else {
            nomes.add(nome);
            System.out.println("Nome adicionado com sucesso.");
        }
    }

    private static void listarNomes() {
        if (nomes.isEmpty()) {
            System.out.println("A lista está vazia.");
            return;
        }
        List<String> nomesOrdenados = new ArrayList<>(nomes);
        Collections.sort(nomesOrdenados);
        System.out.println("\nLista de nomes:");
        for (int i = 0; i < nomesOrdenados.size(); i++) {
            System.out.printf("%d - %s%n", i + 1, nomesOrdenados.get(i));
        }
    }

    private static void removerNome() {
        System.out.print("Digite o nome a ser removido: ");
        String nome = scanner.nextLine().trim();
        if (nomes.remove(nome)) {
            System.out.println("Nome removido com sucesso.");
        } else {
            System.out.println("Nome não encontrado.");
        }
    }

    private static void buscarNome() {
        System.out.print("Digite o nome a ser buscado: ");
        String nome = scanner.nextLine().trim();
        if (nomes.contains(nome)) {
            System.out.println("Nome encontrado na lista.");
        } else {
            System.out.println("Nome não encontrado.");
        }
    }

    private static void salvarNomes() {
        try (FileWriter writer = new FileWriter(ARQUIVO_JSON)) {
            new Gson().toJson(nomes, writer);
            System.out.println("Nomes salvos com sucesso no arquivo " + ARQUIVO_JSON);
        } catch (IOException e) {
            System.out.println("Erro ao salvar nomes: " + e.getMessage());
        }
    }

    private static void carregarNomes() {
        try (FileReader reader = new FileReader(ARQUIVO_JSON)) {
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            nomes = new Gson().fromJson(reader, listType);
            if (nomes == null) nomes = new ArrayList<>();
            System.out.println("Nomes carregados com sucesso do arquivo " + ARQUIVO_JSON);
        } catch (IOException e) {
            System.out.println("Arquivo de nomes não encontrado. Iniciando com lista vazia.");
            nomes = new ArrayList<>();
        }
    }
}