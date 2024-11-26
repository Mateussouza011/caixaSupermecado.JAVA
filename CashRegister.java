import java.util.ArrayList;
import java.util.List;

public class CashRegister {
    private double openingValue;
    private double cashValue;
    private double lastCashValue;
    private List<Double> dailyProfits;
    private List<Double> openingValues;
    private List<Double> closingValues;

    public CashRegister(double lastCashValue) {
        this.lastCashValue = lastCashValue;
        this.dailyProfits = new ArrayList<>();
        this.openingValues = new ArrayList<>();
        this.closingValues = new ArrayList<>();
    }

    public double getOpeningValue() {
        return openingValue;
    }

    public double getCashValue() {
        return cashValue;
    }

    public double getLastCashValue() {
        return lastCashValue;
    }

    public void openCashRegister(double value) {
        this.openingValue = value;
        this.cashValue = value;
        this.openingValues.add(value);
    }

    public void addToCash(double amount) {
        cashValue += amount;
    }

    public void withdrawFromCash(double amount) {
        if (amount <= lastCashValue) {
            lastCashValue -= amount;
        } else {
            System.out.println("Valor de retirada excede o valor em caixa.");
        }
    }

    public void closeCashRegister() {
        this.closingValues.add(this.cashValue);
        this.lastCashValue = this.cashValue; // Atualiza o lastCashValue com o valor de fechamento
        System.out.println("Caixa fechado. Lucro do dia: R$" + String.format("%.2f", cashValue - openingValue));
    }

    public List<Double> getDailyProfits() {
        return dailyProfits;
    }

    public List<Double> getOpeningValues() {
        return openingValues;
    }

    public List<Double> getClosingValues() {
        return closingValues;
    }
}
