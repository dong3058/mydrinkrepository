package drinkselector.drinks.Etcs.Enums;

public enum DrinkType {


    Beer(1L),Wine(2L);


    private Long type_number;

    DrinkType(Long type_number) {
        this.type_number = type_number;
    }


    public Long getType_number() {
        return type_number;
    }
}
