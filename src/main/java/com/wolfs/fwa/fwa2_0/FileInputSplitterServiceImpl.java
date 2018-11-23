package com.wolfs.fwa.fwa2_0;

import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;

public class FileInputSplitterServiceImpl implements FileInputSplitterService{

    @Override
    public Map<String, String> splitStringIntoAttributes(String input) {
        Map<String, String> result = new HashMap<>();
        for (String attribute : StringUtils.split(input, ";")) {
            String[] attributeArray = StringUtils.split(attribute, "=");
            result.put(attributeArray[0], attributeArray[1]);
        }
        return result;
    }

}
