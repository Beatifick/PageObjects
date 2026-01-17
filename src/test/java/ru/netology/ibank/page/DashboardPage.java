package ru.netology.ibank.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final ElementsCollection cards = $$(".list__item div");

    // Получение баланса по номеру карты
    public int getCardBalance(String cardNumber) {
        for (SelenideElement card : cards) {
            if (card.text().contains(cardNumber)) {
                return extractBalance(card.text());
            }
        }
        throw new RuntimeException("Карта не найдена: " + cardNumber);
    }

    // Выбор карты для пополнения
    public void selectCardToTransfer(String cardNumber) {
        for (SelenideElement card : cards) {
            if (card.text().contains(cardNumber)) {
                card.$("button").click(); // нажимаем кнопку "Пополнить"
                return;
            }
        }
        throw new RuntimeException("Карта не найдена: " + cardNumber);
    }

    // Печать балансов всех карт в консоль
    public void printAllBalances() {
        System.out.println("=== Баланс всех карт ===");
        for (SelenideElement card : cards) {
            String text = card.text();
            int balance = extractBalance(text);
            String number = text.split(",")[0]; // берём номер карты до запятой
            System.out.println(number + " → " + balance + " р.");
        }
        System.out.println("========================");
    }

    // Вырезаем баланс из текста
    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), finish).replaceAll("\\s", "");
        return Integer.parseInt(value);
    }
}
