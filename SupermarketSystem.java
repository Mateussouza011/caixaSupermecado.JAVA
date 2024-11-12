import java.util.List;
import java.util.Scanner;

public class SupermarketSystem {
    private static Inventory inventory = new Inventory();
    private static CashRegister cashRegister = new CashRegister(0.0);
    private static Scanner scanner = new Scanner(System.in);
    private static int currentDay = 0;

    public static void main(String[] args) {
        startScreen();
    }

    private static void startScreen() {
        while (true) {
            System.out.println("Valor em caixa: R$" + String.format("%.2f", cashRegister.getLastCashValue()));
            System.out.println("1- Abrir Caixa");
            System.out.println("2- Retirada de Montante");
            System.out.println("3- Emitir Relatório");
            System.out.println("4- Sair");
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    openCashRegister();
                    break;
                case "2":
                    withdrawAmount();
                    break;
                case "3":
                    issueReport();
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void openCashRegister() {
        System.out.println("Deseja abrir caixa com último valor registrado em caixa?");
        System.out.println("1- Sim");
        System.out.println("2- Não");
        System.out.print("Escolha uma opção: ");
        String option = scanner.nextLine();

        if (option.equals("1")) {
            cashRegister.openCashRegister(cashRegister.getLastCashValue());
        } else if (option.equals("2")) {
            System.out.print("Qual o valor que deseja abrir o caixa? ");
            double value = Double.parseDouble(scanner.nextLine());
            cashRegister.openCashRegister(value);
        } else {
            System.out.println("Opção inválida.");
            return;
        }

        currentDay++;
        mainMenu();
    }

    private static void withdrawAmount() {
        System.out.print("Quanto vai ser retirado? ");
        double amount = Double.parseDouble(scanner.nextLine());
        cashRegister.withdrawFromCash(amount);
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("1- Abrir Nova Compra");
            System.out.println("2- Buscar por Item");
            System.out.println("3- Estoque");
            System.out.println("4- Fechar Caixa");
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    newPurchase();
                    break;
                case "2":
                    searchItem();
                    break;
                case "3":
                    manageStock();
                    break;
                case "4":
                    cashRegister.closeCashRegister();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void newPurchase() {
        Sale sale = new Sale();

        while (true) {
            System.out.println("1- Adicionar Item");
            System.out.println("2- Remover Compra");
            System.out.println("3- Encerrar Compra");
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addItemToSale(sale);
                    break;
                case "2":
                    removeItemFromSale(sale);
                    break;
                case "3":
                    closeSale(sale);
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void addItemToSale(Sale sale) {
        System.out.print("Insira o código do produto: ");
        String code = scanner.nextLine();
        Product product = inventory.getProductByCode(code);
        if (product != null) {
            System.out.print("Quantidade: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= product.getStockQuantity()) {
                sale.addItem(product, quantity);
            } else {
                System.out.println("Quantidade indisponível em estoque.");
            }
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void removeItemFromSale(Sale sale) {
        List<ItemSale> items = sale.getItems();
        if (items.isEmpty()) {
            System.out.println("Carrinho vazio.");
            return;
        }
        System.out.println("Produtos no carrinho:");
        for (int i = 0; i < items.size(); i++) {
            ItemSale item = items.get(i);
            System.out.println((i + 1) + "- " + item.getProduct().getName() + " - Quantidade: " + item.getQuantity());
        }
        System.out.print("Digite o número do item que deseja remover: ");
        int itemNumber = Integer.parseInt(scanner.nextLine()) - 1;
        if (itemNumber >= 0 && itemNumber < items.size()) {
            System.out.print("Quantidade a remover: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            sale.removeItem(itemNumber, quantity);
            System.out.println("Item removido com sucesso.");
        } else {
            System.out.println("Item não encontrado.");
        }
    }

    private static void closeSale(Sale sale) {
        System.out.println("Compra encerrada. Detalhes da compra:");
        for (ItemSale item : sale.getItems()) {
            System.out.println("Produto: " + item.getProduct().getName()
                    + " - Quantidade: " + item.getQuantity()
                    + " - Valor Total: R$" + String.format("%.2f", item.getTotalValue()));
        }
        System.out.println("Valor total da compra: R$" + String.format("%.2f", sale.getTotalValue()));
        cashRegister.addToCash(sale.getTotalValue());
    }

    private static void searchItem() {
        while (true) {
            System.out.println("1- Listar Todos os Produtos");
            System.out.println("2- Buscar");
            System.out.println("3- Voltar");
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    listAllProducts();
                    break;
                case "2":
                    searchProductByName();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void listAllProducts() {
        List<Product> products = inventory.getAllProductsSorted();
        for (Product product : products) {
            System.out.println("Nome: " + product.getName()
                    + " - Código: " + product.getCode()
                    + " - Quantidade em estoque: " + product.getStockQuantity());
        }
    }

    private static void searchProductByName() {
        System.out.print("Digite o nome do produto: ");
        String name = scanner.nextLine();
        List<Product> results = inventory.searchProductByName(name);
        for (Product product : results) {
            System.out.println("Nome: " + product.getName()
                    + " - Código: " + product.getCode()
                    + " - Quantidade em estoque: " + product.getStockQuantity());
        }
    }

    private static void issueReport() {
        while (true) {
            System.out.println("1- Relatório de Vencimento de Produtos");
            System.out.println("2- Relatório de Unidades de Produtos");
            System.out.println("3- Relatório de Lucro Diário");
            System.out.println("4- Voltar");
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    reportProductValidity();
                    break;
                case "2":
                    reportProductStock();
                    break;
                case "3":
                    reportDailyProfit();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void reportProductValidity() {
        System.out.print("Pesquisar produtos com validade de até quantos dias? ");
        int days = Integer.parseInt(scanner.nextLine());
        List<Product> products = inventory.getProductsByValidity(days);
        for (Product product : products) {
            System.out.println("Nome: " + product.getName()
                    + " - Validade: " + product.getValidity() + " dias");
        }
    }

    private static void reportProductStock() {
        System.out.print("Pesquisar produtos com quantas unidades? ");
        int quantity = Integer.parseInt(scanner.nextLine());
        List<Product> products = inventory.getProductsByStock(quantity);
        for (Product product : products) {
            System.out.println("Nome: " + product.getName()
                    + " - Quantidade em estoque: " + product.getStockQuantity());
        }
    }

    private static void reportDailyProfit() {
        List<Double> profits = cashRegister.getDailyProfits();
        List<Double> openingValues = cashRegister.getOpeningValues();
        List<Double> closingValues = cashRegister.getClosingValues();
        double totalProfit = 0.0;
        for (int i = 0; i < profits.size(); i++) {
            double profit = profits.get(i);
            totalProfit += profit;
            System.out.println((i + 1) + "º Dia - Lucro: R$" + String.format("%.2f", profit)
                    + " (Abertura: R$" + String.format("%.2f", openingValues.get(i))
                    + ", Fechamento: R$" + String.format("%.2f", closingValues.get(i)) + ")");
        }
        System.out.println("Total de dias registrados: " + profits.size());
        System.out.println("Lucro total: R$" + String.format("%.2f", totalProfit));
    }

    private static void manageStock() {
        while (true) {
            System.out.println("1- Adicionar Novo Produto");
            System.out.println("2- Adicionar Estoque a Produto Existente");
            System.out.println("3- Remover Produto");
            System.out.println("4- Voltar");
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addNewProduct();
                    break;
                case "2":
                    addStockToExistingProduct();
                    break;
                case "3":
                    removeProduct();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void addNewProduct() {
        System.out.println("Escolha a categoria:");
        System.out.println("1- Alimentos e Utensílios do Lar");
        System.out.println("2- Eletrodomésticos e Eletrônicos");
        System.out.print("Escolha uma opção: ");
        String category = scanner.nextLine();

        System.out.print("Digite o nome: ");
        String name = scanner.nextLine();
        System.out.print("Digite o código: ");
        String code = scanner.nextLine();
        System.out.print("Digite o valor: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Digite a quantidade em estoque: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine());

        if (category.equals("1")) {
            System.out.print("Digite a validade em dias: ");
            int validity = Integer.parseInt(scanner.nextLine());
            FoodProduct newProduct = new FoodProduct(name, code, price, stockQuantity, validity);
            inventory.addProduct(newProduct);
        } else if (category.equals("2")) {
            System.out.print("Digite a garantia em dias: ");
            int warranty = Integer.parseInt(scanner.nextLine());
            ElectronicProduct newProduct = new ElectronicProduct(name, code, price, stockQuantity, warranty);
            inventory.addProduct(newProduct);
        } else {
            System.out.println("Categoria inválida.");
        }
    }

    private static void addStockToExistingProduct() {
        System.out.print("Digite o código do produto: ");
        String code = scanner.nextLine();
        Product product = inventory.getProductByCode(code);
        if (product != null) {
            System.out.print("Quantas unidades deseja adicionar ao estoque? ");
            int quantity = Integer.parseInt(scanner.nextLine());
            product.addStock(quantity);
            System.out.println("Estoque atualizado com sucesso.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void removeProduct() {
        System.out.print("Digite o código do produto que será removido: ");
        String code = scanner.nextLine();
        Product product = inventory.getProductByCode(code);
        if (product != null) {
            System.out.print("Confirmar remoção? (1-Sim / 2-Não): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equals("1")) {
                inventory.removeProduct(code);
                System.out.println("Produto removido com sucesso.");
            } else {
                System.out.println("Remoção cancelada.");
            }
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
}
//felipe gayzao