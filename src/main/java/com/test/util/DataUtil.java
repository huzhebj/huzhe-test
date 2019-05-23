package com.test.util;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {

    public static Map<String, Object> sendResponse(String code, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", "000".equals(code) ? "执行成功" : message);
        map.put("success", "000".equals(code));
        return map;
    }
}
