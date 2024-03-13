package pl.pacinho.expensetrackersystem.tools;

import com.google.gson.JsonElement;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;

class LocalDateAdapterTest {

    @Test
    void serializeShouldReturnedStringDateInIsoFormat() {
        //given
        LocalDate date = LocalDate.of(2024,3,12);

        //when
        JsonElement serialize = new LocalDateAdapter().serialize(date, null, null);

        //when
        assertThat(serialize.toString(), equalTo("\"2024-03-12\""));
    }

}