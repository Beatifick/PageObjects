package ru.netology.ibank.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.ibank.data.DataHelper.CardInfo;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    // Получить баланс карты по объекту CardInfo
    public int getCardBalance(CardInfo card) {
        SelenideElement element = $("[data-test-id='" + card.getId() + "']");
        element.shouldBe(visible);
        String text = element.getText(); // пример: "Balance: 10000 ₽"
        String digits = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(digits);
    }

    // Выбрать карту для пополнения и перейти на страницу перевода
    public TransferPage selectCardToDeposit(CardInfo card) {
        $("[data-test-id='" + card.getId() + "'] [data-test-id=action-deposit]").click();
        return new TransferPage();
    }
}
