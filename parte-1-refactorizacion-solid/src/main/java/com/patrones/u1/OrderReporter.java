package com.patrones.u1;

import java.util.List;

public class OrderReporter {

    public void print(List<String> orders) {
        System.out.println("=== Reporte de Órdenes ===");

        for (String order : orders) {
            System.out.println("  " + order);
        }
    }
}