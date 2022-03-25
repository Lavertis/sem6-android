package com.lavertis.project2.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhoneSeedData {
    public final static List<Phone> phoneList = new ArrayList<>(
            Arrays.asList(
                    new Phone(
                            "Samsung",
                            "Galaxy S22",
                            12,
                            "https://www.mgsm.pl/pl/katalog/samsung/galaxys22/"),
                    new Phone(
                            "Samsung",
                            "Galaxy S8+",
                            9,
                            "https://www.mgsm.pl/pl/katalog/samsung/galaxys8plus/"),
                    new Phone(
                            "Samsung",
                            "Galaxy A52 5G",
                            11,
                            "https://www.mgsm.pl/pl/katalog/samsung/galaxya52s5g/"),
                    new Phone(
                            "One Plus",
                            "9 Pro",
                            11,
                            "https://www.mgsm.pl/pl/katalog/oneplus/9pro/"),
                    new Phone(
                            "Xiaomi",
                            "11T",
                            11,
                            "https://www.mgsm.pl/pl/katalog/xiaomi/11t/")
            ));
}
