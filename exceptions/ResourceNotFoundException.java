package com.blogappapi.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
String resourceName;
String fieldName;
long fieldValue;

String stringFieldVlaue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with this %s: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, String stringFieldVlaue) {
        super(String.format("%s not found with this %s: %s", resourceName, fieldName, stringFieldVlaue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.stringFieldVlaue = stringFieldVlaue;
    }
}
