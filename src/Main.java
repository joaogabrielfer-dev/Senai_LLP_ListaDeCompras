import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner ler = new Scanner(System.in);
    static ArrayList<ListaCompras> listas = new ArrayList<>();

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n.-------------------.");
            System.out.println("| Gestão de compras |");
            System.out.println("'-------------------'");
            System.out.println("Selecione a opção:");
            System.out.println("1. Nova lista");
            System.out.println("2. Fazer compras");
            System.out.println("3. Relatório");
            System.out.println("0. Sair");

            System.out.print("\n>> Opção: ");
            opcao = ler.nextInt();
            ler.nextLine();

            switch (opcao) {
                case 1:
                    novaLista();
                    break;

                case 2:
                    fazerCompras();
                    break;

                case 3:
                    relatorio();
                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    public static void novaLista() {

        String nomeBase = "lista_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        System.out.print("\n>> Nova lista, informe o nome [" + nomeBase + "]: ");
        String nome = ler.nextLine();

        if (nome.isBlank()) {
            nome = nomeBase;
        }

        ListaCompras lista = new ListaCompras(nome);

        while (true) {

            System.out.println("\n>> ---Informe o item---------");

            System.out.print(">> Descrição: ");
            String descricao = ler.nextLine();

            if (descricao.isBlank()) {
                break;
            }

            System.out.print(">> Unidade (UN, KG, LT): ");
            String unidade = ler.nextLine().toUpperCase();

            System.out.print(">> Quantidade: ");
            double quantidade = ler.nextDouble();
            ler.nextLine();

            lista.itens.add(
                    new Item(descricao, unidade, quantidade)
            );
        }

        listas.add(lista);

        System.out.println(">> ---Lista salva!---------");
    }

    public static ListaCompras escolherLista() {

        if (listas.isEmpty()) {
            System.out.println("Nenhuma lista cadastrada.");
            return null;
        }

        System.out.println("\nListas disponíveis:");

        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + " - " + listas.get(i).nome);
        }

        System.out.print("\nEscolha a lista: ");
        int escolha = ler.nextInt();
        ler.nextLine();

        return listas.get(escolha - 1);
    }

    public static void fazerCompras() {

        ListaCompras lista = escolherLista();

        if (lista == null) {
            return;
        }

        System.out.println("\n>> ---Fazer compras [" + lista.nome + "]---");

        double total = 0;

        for (int i = 0; i < lista.itens.size(); i++) {

            Item item = lista.itens.get(i);

            System.out.println("\n>> (" + (i + 1) + "/" + lista.itens.size() + ") Produto " + item.descricao + " " + item.quantidade + " " + item.unidade);

            System.out.print(">> Quantidade [" + item.quantidade + " " + item.unidade + "]: ");

            String qtd = ler.nextLine();

            if (!qtd.isBlank()) {
                item.quantidade = Double.parseDouble(qtd);
            }

            System.out.print(">> Preço: ");
            item.preco = ler.nextDouble();
            ler.nextLine();

            total += item.preco * item.quantidade;
        }

        System.out.println("\n>> ---Total------------------");
        System.out.printf(">> R$: %.2f%n", total);
    }

    public static void relatorio() {

        ListaCompras lista = escolherLista();

        if (lista == null) {
            return;
        }

        double total = 0;

        System.out.println("\n>> ---Relatório [" + lista.nome + "]---");
        System.out.println(">> Item, Descrição, Qtd, UN, Preço");

        for (int i = 0; i < lista.itens.size(); i++) {

            Item item = lista.itens.get(i);

            System.out.printf(">> %d, %s, %.2f, %s, %.2f%n", (i + 1), item.descricao, item.quantidade, item.unidade, item.preco);

            total += item.preco * item.quantidade;
        }

        System.out.printf(">> 0, TOTAL, %d, UN, %.2f%n", lista.itens.size(), total);
    }
}

class Item {

    String descricao;
    String unidade;
    double quantidade;
    double preco;

    public Item(String descricao, String unidade, double quantidade) {
        this.descricao = descricao;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.preco = 0;
    }
}

class ListaCompras {

    String nome;
    ArrayList<Item> itens;

    public ListaCompras(String nome) {
        this.nome = nome;
        this.itens = new ArrayList<>();
    }
}