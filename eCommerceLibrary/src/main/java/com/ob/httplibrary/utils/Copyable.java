package com.ob.httplibrary.utils;

/**
 * interface for marking that a class can be copied
 *
 * @param <Type>
 */
public interface Copyable<Type> {
    Type copy();

}
