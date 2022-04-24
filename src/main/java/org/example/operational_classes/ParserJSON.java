package org.example.operational_classes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data.Order;
import org.example.exceptions.NotAJSONOrWrongJSONFormat;

public class ParserJSON {

    public Order parse(String line) throws NotAJSONOrWrongJSONFormat {
        Order order;
        ObjectMapper mapper = new ObjectMapper();
        try {
            order = mapper.readValue(line, Order.class);
        } catch (JsonProcessingException e) {
            throw new NotAJSONOrWrongJSONFormat("Проверьте соответствие данных внутри файла с форматом JSON, а также соответствие формату входящих данных");
        }
        return order;
    }
}
