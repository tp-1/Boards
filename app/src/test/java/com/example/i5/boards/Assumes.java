package com.example.i5.boards;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a test case (class) assumes listed test cases ran without error.
 * Just chills above a class, doesn't do anything.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Inherited
public @interface Assumes {
    Class<?> value();
}
