package edu.school21.info21.services.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ExportMapper {

    public byte[] convert (final List<Object[]> rawData) {
        String temp = "";
        for (Object[] i : rawData) {
            for (Object j : i) {
                temp += String.valueOf(j);
            }
            temp += '\n';
        }
        return temp.getBytes();
    }
}
