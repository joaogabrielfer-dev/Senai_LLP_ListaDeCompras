import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaCompras {

    private Scanner ler = new Scanner(System.in);
    private ArrayList<ListaCompras> listas = new ArrayList<>();

    public void iniciar() {

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

    private void novaLista() {

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

            lista.adicionarItem(new Item(descricao, unidade, quantidade));
        }

        listas.add(lista);

        System.out.println(">> ---Lista salva!---------");
    }

    private ListaCompras escolherLista() {

        if (listas.isEmpty()) {
            System.out.println("Nenhuma lista cadastrada.");
            return null;
        }

        System.out.println("\nListas disponíveis:");

        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + " - " + listas.get(i).getNome());
        }

        System.out.print("\nEscolha a lista: ");
        int escolha = ler.nextInt();
        ler.nextLine();

        if (escolha < 1 || escolha > listas.size()) {
            System.out.println("Lista inválida.");
            return null;
        }

        return listas.get(escolha - 1);
    }

    private void fazerCompras() {

        ListaCompras lista = escolherLista();

        if (lista == null) {
            return;
        }

        double total = 0;

        System.out.println("\n>> ---Fazer compras [" + lista.getNome() + "]---");

        for (int i = 0; i < lista.getItens().size(); i++) {

            Item item = lista.getItens().get(i);

            System.out.println("\n>> (" + (i + 1) + "/" + lista.getItens().size() + ") Produto " + item.getDescricao() + " " + item.getQuantidade() + " " + item.getUnidade());

            System.out.print(">> Quantidade [" + item.getQuantidade() + " " + item.getUnidade() + "]: ");

            String qtd = ler.nextLine();

            if (!qtd.isBlank()) {
                item.setQuantidade(Double.parseDouble(qtd));
            }

            System.out.print(">> Preço: ");
            item.setPreco(ler.nextDouble());
            ler.nextLine();

            total += item.getTotal();
        }

        System.out.printf("\n>> Total: R$ %.2f%n", total);
    }

    private void relatorio() {

        ListaCompras lista = escolherLista();

        if (lista == null) {
            return;
        }

        double total = 0;

        System.out.println("\n>> ---Relatório [" + lista.getNome() + "]---");

        System.out.println("Item, Descrição, Qtd, UN, Preço");

        for (int i = 0; i < lista.getItens().size(); i++) {

            Item item = lista.getItens().get(i);

            System.out.printf("%d, %s, %.2f, %s, %.2f%n", i + 1, item.getDescricao(), item.getQuantidade(), item.getUnidade(), item.getPreco());

            total += item.getTotal();
        }

        System.out.printf("0, TOTAL, %d, UN, %.2f%n", lista.getItens().size(), total);
    }
}
