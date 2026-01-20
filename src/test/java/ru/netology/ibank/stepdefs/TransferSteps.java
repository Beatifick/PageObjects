package ru.netology.ibank.stepdefs;

import io.cucumber.java.ru.*;
import ru.netology.ibank.data.DataHelper;
import ru.netology.ibank.data.DataHelper.CardInfo;
import ru.netology.ibank.page.DashboardPage;
import ru.netology.ibank.page.TransferPage;
import ru.netology.ibank.page.LoginPage;
import ru.netology.ibank.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferSteps {

    private LoginPage loginPage;
    private VerificationPage verificationPage;
    private DashboardPage dashboardPage;
    private TransferPage transferPage;

    private int transferAmount;

    @Дано("пользователь залогинен с именем {string} и паролем {string}")
    public void login(String login, String password) {
        loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.login(login, password);

        verificationPage = new VerificationPage();
        verificationPage.verify(DataHelper.getVerificationCode().getCode());

        dashboardPage = new DashboardPage();
    }

    @Когда("пользователь переводит {int} рублей с карты {int} на свою {int} карту с главной страницы")
    public void transferMoneyByCardIndex(int amount, int fromCardIndex, int toCardIndex) {
        transferAmount = amount;

        CardInfo fromCard = (fromCardIndex == 1) ? DataHelper.getFirstCard() : DataHelper.getSecondCard();
        CardInfo toCard = (toCardIndex == 1) ? DataHelper.getFirstCard() : DataHelper.getSecondCard();

        // Выбираем карту для пополнения (получатель)
        transferPage = dashboardPage.selectCardToDeposit(toCard);

        // Вводим сумму и карту отправителя, затем перевод
        transferPage.transfer(amount, fromCard.getNumber());
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void cardBalanceShouldBeByIndex(int cardIndex, int expectedBalance) {
        CardInfo card = (cardIndex == 1) ? DataHelper.getFirstCard() : DataHelper.getSecondCard();
        int actual = dashboardPage.getCardBalance(card);
        assertEquals(expectedBalance, actual);
    }
}
