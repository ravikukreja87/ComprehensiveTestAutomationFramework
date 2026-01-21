package ui.web.taf.utils.data;

import com.github.javafaker.Faker;

public class FakerUtils {

    private static final Faker faker = new Faker();

    public static String generateRandomName() {
        return faker.name().fullName();
    }

    public static String generateRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String generateRandomAddress() {
        return faker.address().fullAddress();
    }

    public static String generateCreditCardNumber() {
        return faker.finance().creditCard();
    }
}
