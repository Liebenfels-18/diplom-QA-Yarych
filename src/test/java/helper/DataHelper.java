package helper;

import com.github.javafaker.Faker;
import data.CardModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {
    public static Faker faker = new Faker();

    public static String getValidName() {
        return faker.name().fullName();
    }//1

    public static String getMonth(int shiftedMonth){
        LocalDate date = LocalDate.now().plusMonths(shiftedMonth);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return formatter.format(date);
    }

    public static String getYear(int shiftedYear){
        LocalDate date = LocalDate.now().plusYears(shiftedYear);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YY");
        return formatter.format(date);
    }

    public static String cvcCode() {
        return String.valueOf(faker.number().numberBetween(100, 999));
    }

    public static String numberCards(int number) {
        return CardModel.numberCard[number];
    }

    private DataHelper() {
    }

    public static CardModel validInfo() {
        return new CardModel(getMonth(1), getYear(1), getValidName(), cvcCode());
    }
}