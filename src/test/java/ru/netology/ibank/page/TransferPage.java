package ru.netology.ibank.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement fromField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public void transfer(String fromCard, int amount) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(fromCard);
        transferButton.click();
    }
}
