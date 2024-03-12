package com.study.grpc.api.gw.service;

import java.util.List;

public class Randomizer {

    private final static List<String> brands = List.of("Lamborghini", "Nissan", "Fiat", "Dodge", "Chevrolet",
            "Ferrari", "BMW", "Opel", "Porsche", "Bugatti", "Mercedes-Benz", "Seat", "Ford", "Audi", "Honda", "Lexus",
            "Acura", "KIA", "Jaguar", "Volkswagen", "Mazda", "Maserati", "Jeep", "Toyota", "Cadillac", "Subaru",
            "Alfa Romeo", "Mitsubishi", "Renault", "Peugeot");

    private final static List<String> states = List.of("Alabama", "Alaska", "Arizona", "Arkansas", "California",
            "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
            "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
            "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "Wyoming",
            "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
            "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin");

    public static String randomBrand() {
        return brands.get((int) (Math.random() * brands.size()));
    }

    public static String randomState() {
        return states.get((int) (Math.random() * states.size()));
    }

    public static int randomPrice() {
        return (int) (Math.random() * 80000) + 20000;
    }
}
