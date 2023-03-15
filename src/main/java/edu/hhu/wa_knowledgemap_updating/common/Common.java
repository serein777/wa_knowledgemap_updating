package edu.hhu.wa_knowledgemap_updating.common;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Data
public class Common {

    Set<String> streamTypeSet;


    public Common() {
        streamTypeSet = new HashSet<>();
        streamTypeSet.add("大型河");
        streamTypeSet.add("中型河");
        streamTypeSet.add("小型河");
    }


}