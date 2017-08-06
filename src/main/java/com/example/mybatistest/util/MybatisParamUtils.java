package com.example.mybatistest.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiazhi
 * @since 2017/8/6
 */
public class MybatisParamUtils {

    public static Map<String, Object> paramMap(Object... param) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < param.length; i++) {
            map.put(String.valueOf(i + 1), param);
        }

        return map;
    }
}
