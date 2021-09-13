package ru.alaev.library_application.service.execption;

public class StyleNotFoundException extends RuntimeException {
    public StyleNotFoundException(String style) {
        super(String.format("There is no style with this name or ID %s", style));
    }
}
