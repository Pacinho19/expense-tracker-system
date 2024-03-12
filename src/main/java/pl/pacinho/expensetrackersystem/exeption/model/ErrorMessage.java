package pl.pacinho.expensetrackersystem.exeption.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMessage {

    INTERNAL_SERVER_ERROR("Server Error. Please check your server logs for more information.");

    @Getter
    private final String text;
}
