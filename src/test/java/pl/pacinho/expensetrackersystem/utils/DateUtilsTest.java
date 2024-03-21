package pl.pacinho.expensetrackersystem.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

class DateUtilsTest {

    @ParameterizedTest
    @MethodSource("localDatesWithNull")
    void falseShouldBeReturnedWhenOneOfGivenDatesIsNull(LocalDate date1, LocalDate date2, LocalDate date3) {
        //given

        //when
        boolean isBetween = DateUtils.isBetween(date1, date2, date3);

        //then
        assertThat(isBetween, is(false));
    }

    private static Stream<Arguments> localDatesWithNull() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(LocalDate.now(), null, LocalDate.now()),
                Arguments.of(LocalDate.now(), LocalDate.now(), null),
                Arguments.of(null, null, LocalDate.now()),
                Arguments.of(LocalDate.now(), null, null),
                Arguments.of(null, LocalDate.now(), null)
        );
    }

    @Test
    void falseShouldBeReturnedWhenStartDateIsAfterEndDate() {
        //given
        LocalDate startDate = LocalDate.now().plusDays(5);
        LocalDate endDate = LocalDate.now();
        LocalDate date = LocalDate.now().plusDays(1);

        //when
        boolean isBetween = DateUtils.isBetween(date, startDate, endDate);

        //then
        assertThat(isBetween, is(false));
    }

    @Test
    void falseShouldBeReturnedWhenDateIsBeforeStartDate() {
        //given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        LocalDate date = startDate.minusDays(1);

        //when
        boolean isBetween = DateUtils.isBetween(date, startDate, endDate);

        //then
        assertThat(isBetween, is(false));
    }

    @Test
    void falseShouldBeReturnedWhenDateIsAfterEndDate() {
        //given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        LocalDate date = endDate.plusDays(1);

        //when
        boolean isBetween = DateUtils.isBetween(date, startDate, endDate);

        //then
        assertThat(isBetween, is(false));
    }

    @Test
    void trueShouldBeReturnedWhenDateIsEqualToStartDate() {
        //given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        LocalDate date = LocalDate.now();

        //when
        boolean isBetween = DateUtils.isBetween(date, startDate, endDate);

        //then
        assertThat(isBetween, is(true));
    }

    @Test
    void trueShouldBeReturnedWhenDateIsBetweenStartDateAndEndDate() {
        //given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        LocalDate date = LocalDate.now().plusDays(1);

        //when
        boolean isBetween = DateUtils.isBetween(date, startDate, endDate);

        //then
        assertThat(isBetween, is(true));
    }

    @Test
    void trueShouldBeReturnedWhenDateIsEqualToEndDate() {
        //given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        LocalDate date = LocalDate.now().plusDays(5);

        //when
        boolean isBetween = DateUtils.isBetween(date, startDate, endDate);

        //then
        assertThat(isBetween, is(true));
    }

    @Test
    void nullShouldBeReturnedWhenGivenLocalDateTimeIsNull() {
        //given
        LocalDateTime localDate = null;

        //when
        Date date = DateUtils.convert(localDate);


        //then
        assertThat(date, nullValue());
    }

    @Test
    void dateShouldBeReturnedWhenGivenLocalDateTimeIsNotNull() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0));

        //when
        Date date = DateUtils.convert(localDateTime);


        //then
        assertThat(date, notNullValue());
        assertThat(date, instanceOf(java.util.Date.class));
        assertThat(date.getTime(), equalTo(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
    }

}