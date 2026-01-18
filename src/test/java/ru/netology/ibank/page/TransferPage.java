package ru.netology.ibank.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    // Выполнить перевод: ввод суммы и карты отправителя
    public void transfer(int amount, String fromCardNumber) {
        amountField.shouldBe(visible).setValue(String.valueOf(amount));
        fromField.shouldBe(visible).setValue(fromCardNumber);
        transferButton.click();
    }

    // Вернуться на дашборд после перевода
    public DashboardPage returnToDashboard() {
        return new DashboardPage();
    }
}
