package pl.pacinho.expensetrackersystem.exeption.utils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class CustomExceptionMessageUtils {

    public static String getEnumErrorMessage(Exception e) {
        if (e.getCause() != null && e.getCause() instanceof InvalidFormatException) {
            String message = e.getCause().getMessage();
            if (message.contains("values accepted for Enum class: [")) {
                return "Enum value should be: [" + message.split("\\[")[1].split("\\]")[0] + "]";
            }
        }
        return e.getMessage();
    }
}
