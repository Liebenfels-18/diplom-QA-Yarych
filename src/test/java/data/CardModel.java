package data;

import lombok.Data;

@Data
public class CardModel {
    public static final String[] numberCard = {"4444_4444_4444_4441", "4444_4444_4444_4442", "4444_4444", ""};
    private final String month;
    private final String year;
    private final String name;
    private final String cvc;
}