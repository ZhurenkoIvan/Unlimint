package org.example.operational_classes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data.Order;
import org.example.dto.OrderDTO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Converter {

    private static final OrderId orderId = new OrderId();

    public void convertToDTO(Order order, String result, int line, String path) {
        OrderDTO orderDTO = new OrderDTO();
        synchronized (orderId) {
            orderId.id++;
            orderDTO.setId(orderId.id);
            orderDTO.setLine(line);
            orderDTO.setFileName(getFileName(path));
        }
        if (result.equals("OK")) {
            orderDTO.setAmount(order.getAmount());
            orderDTO.setCurrency(order.getCurrency());
            orderDTO.setComment(order.getComment());
            orderDTO.setResult(result);
        } else {
            orderDTO.setResult(result);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            String JSONOrderDTO = mapper.writeValueAsString(orderDTO);
            System.out.println(JSONOrderDTO);
        } catch (JsonProcessingException ignored) {

        }
    }

    private String getFileName(String path) {
        Pattern pattern = Pattern.compile("[a-zA-zА-я0-9]+\\.[a-zA-zА-я]+");
        Matcher matcher = pattern.matcher(path);
        String fileName = path;
        while (matcher.find()) {
            fileName = matcher.group();
        }
        return fileName;
    }


    static class OrderId {
        private int id = 0;
    }
}

