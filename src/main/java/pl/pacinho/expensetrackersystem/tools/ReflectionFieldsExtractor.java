package pl.pacinho.expensetrackersystem.tools;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public class ReflectionFieldsExtractor {
    public static Stream<Field> getFieldsStream(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields());
    }
}
