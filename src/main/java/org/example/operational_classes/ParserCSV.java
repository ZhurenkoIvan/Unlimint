package org.example.operational_classes;

import org.example.data.Order;
import org.example.enums.Currency;
import org.example.exceptions.WrongLineFormatException;
import org.example.exceptions.WrongNumbersException;


public class ParserCSV {

    public Order parse(String line) throws WrongLineFormatException {
        String[] fields = line.split(",");
        if (fields.length != 4) {
            throw new WrongLineFormatException();
        }
        Order order = new Order();
        try {
            order.setOrderId(Integer.parseInt(fields[0]));
            order.setAmount(Double.parseDouble(fields[1]));
        } catch (NumberFormatException e) {
            throw new WrongNumbersException();
        }try {
            order.setCurrency(Currency.valueOf(fields[2]));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Не обрабатываемая валюта");
        }

        order.setComment(fields[3]);
        return order;
    }
}
