package ru.netology.ibank.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.ibank.data.DataHelper.CardInfo;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$(".list__item"); // все элементы с data-test-id

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    // Получение баланса карты
    public int getCardBalance(int cardIndex) {
        String text = cards.get(cardIndex - 1).getText();
        return extractBalance(text);
    }

    // Выбор карты для пополнения
    public TransferPage selectCardToDeposit(int cardIndex) {
        cards.get(cardIndex - 1)
                .$("[data-test-id=action-deposit]")
                .click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart) + balanceStart.length();
        int end = text.indexOf(balanceFinish, start);
        String value = text.substring(start, end).trim().replace(" ", "");
        return Integer.parseInt(value);
    }
}
