package pl.pacinho.expensetrackersystem.tools;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ReflectionFieldsExtractorTest {


    @Test
    void streamWithTwoElementsShouldBeReturnedWhenGivenClassHasTwoFields() {
        //given
        Class<?> clazz = TestClass.class;

        //when
        Stream<Field> fieldsStream = ReflectionFieldsExtractor.getFieldsStream(clazz);
        List<Field> list = fieldsStream.toList();

        //then
        assertThat(list, hasSize(2));
        assertThat(list, containsInAnyOrder(
                hasProperty("name", equalTo("x")),
                hasProperty("name", equalTo("y"))
        ));
    }

    private static class TestClass {
        private int x;
        private String y;
    }
}