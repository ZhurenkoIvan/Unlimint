package org.example.threads;

import org.example.data.Order;
import org.example.operational_classes.Converter;


public class ConvertThread  extends Thread{

    private final Converter converter;
    private String result;
    private Order order;
    private int line;
    private String path;

    public ConvertThread(Converter converter) {
        this.converter = converter;
    }

    @Override
    public void run() {
        converter.convertToDTO(order, result, line, path);
    }

    public Converter getConverter() {
        return converter;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
