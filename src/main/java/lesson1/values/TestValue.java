package lesson1.values;

import java.util.ArrayList;

public class TestValue {
    public static void main(String[] args) {
        Value<TextValue> v1 = new Value<>(new TextValue("124"));
        v1.getValueInner().getValue();
        Value<ListValue> v2 = new Value<>(new ListValue(new ArrayList<>()));
        v2.getValueInner().getValue();
    }
}
