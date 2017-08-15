package com.wyl.self.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hc-3020-i3
 * @create 2017-07-31 18:01
 * @desc ${DESCRIPTION}
 **/
public class Constants {

    private static Map<String, String> constants;

    @SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
    public Constants(String... resourcesPaths) {
        PropertiesLoader propertiesLoader = new PropertiesLoader(resourcesPaths);
        if(propertiesLoader.getProperties() != null) {
            this.constants = ((Map)propertiesLoader.getProperties());
        }
        else {
            this.constants = new HashMap<String, String>();
        }
    }

    public static boolean containsKey(String key) {
        return constants.containsKey(key);
    }

    public static String valueOf(String key) {
        return constants.get(key);
    }
}
