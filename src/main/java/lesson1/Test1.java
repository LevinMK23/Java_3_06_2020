package lesson1;

import java.util.HashMap;

public class Test1 {
    public static void main(String[] args) throws CloneNotSupportedException {
        HashMap<String, AbstractValue> map = new HashMap<>();
        AbstractValue value = new AbstractValue(12);
        map.put("KEY_FIELD", (AbstractValue) value.clone());
        value.setValue("1234");
        System.out.println(map.get("KEY_FIELD").getValue());
    }
}
