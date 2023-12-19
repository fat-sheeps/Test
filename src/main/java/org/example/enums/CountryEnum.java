package org.example.enums;

import lombok.Getter;

@Getter
public enum CountryEnum {
    NONE(0, "-"),
    QI(1, "齐"),
    CHU(2, "楚"),
    YAN(3, "燕"),
    ZHAO(4, "赵"),
    WEI(5, "魏"),
    HAN(6, "韩"),
    QIN(7, "秦");

    private final Integer code;
    private final String name;

    CountryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CountryEnum getCountryEnum(int index) {
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum element : myArray) {
            if (index == element.getCode()) {
                return element;
            }
        }
        return NONE;
    }
}