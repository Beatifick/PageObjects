package ru.netology.ibank.page;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    public int getCardBalance(String cardId) {
        String text = $("[data-test-id='" + cardId + "']").text();
        String balance = text.substring(
                text.indexOf("баланс: ") + 8,
                text.indexOf(" р.")
        );
        return Integer.parseInt(balance);
    }

    public TransferPage selectCardToDeposit(String cardId) {
        $("[data-test-id='" + cardId + "'] [data-test-id=action-deposit]").click();
        return new TransferPage();
    }
}
