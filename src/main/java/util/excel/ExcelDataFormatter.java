package util.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YLBG-TSY-1502 on 2017/5/16.
 */
public class ExcelDataFormatter {
    private Map<String, Map<String, String>> formatter = new HashMap<String, Map<String, String>>();

    public void set(String key, Map<String, String> map) {
        formatter.put(key, map);
    }

    public Map<String, String> get(String key) {
        return formatter.get(key);
    }
}
