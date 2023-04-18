package ru.vsu.cs.musiczoneserver.exception.validexeption.record;

import java.util.List;


public record ValidationErrorResponse(List<Violation> violations) {

}