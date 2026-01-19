package ru.netology.ibank.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.ibank.data.DataHelper.CardInfo;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$("[data-test-id]"); // все элементы с data-test-id

    // Получение баланса карты
    public int getCardBalance(CardInfo card) {
        SelenideElement cardElement = cards.findBy(Condition.attribute("data-test-id", card.getId()))
                .shouldBe(Condition.visible);
        String text = cardElement.getText();
        return extractBalance(text);
    }

    // Выбор карты для пополнения
    public TransferPage selectCardToDeposit(CardInfo card) {
        cards.findBy(Condition.attribute("data-test-id", card.getId()))
                .shouldBe(Condition.visible)
                .$("[data-test-id=action-deposit]")
                .click();
        return new TransferPage();
    }

    // Вырезаем баланс из текста вида "**** **** **** 0001, баланс: 15000 р."
    private int extractBalance(String text) {
        int start = text.indexOf("баланс: ") + 8;
        int end = text.indexOf(" р.", start);
        String value = text.substring(start, end).trim().replace(" ", "");
        return Integer.parseInt(value);
    }
}
