package org.dorsmedia.validations;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;

public interface Constraint{


    /**
     * valid if value != null (type unchecked)
     */
    Predicate<Object> NOT_NULL = Objects::nonNull;
    /**
     * valid if value == null (unchecked)
     */
    Predicate<Object> MUST_BE_NULL = Objects::isNull;
    /**
     * valid if String value !=null and NOT empty
     */
    Predicate<CharSequence> NOT_EMPTY_TEXT = (value) -> value != null && value.length() > 0;
    /**
     * valid if List value !=null and NOT empty
     */
    Predicate<Collection> NOT_EMPTY_LIST =(value) -> value != null && value.size() > 0;

    /**
     * valid if List value !=null and NOT empty
     */
    Predicate<Collection> EMPTY_LIST =(value) -> value.size() == 0;

    /**
     * @param size max size
     * @return predicate that validate if value !=null and value lenght is smaller than max size

     */
    static  Predicate<CharSequence> MAX_LENGTH(int size) {
        return (value) -> value != null && value.length() < size;
    }
    /**
     * @param size max size
     * @return predicate that validate if value !=null and value lenght is greater than min

     */
    static Predicate<CharSequence> MIN_LENGTH(int size) {
        return (CharSequence value) -> value != null && value.length() > size;
    }
    /**
     * @param min  size
     * @param max  size
     * @return predicate that validate if value !=null and value lenght is smaller than max size and greater than min
     */
    static Predicate<CharSequence> LENGTH_BETWEEN(int min, int max) {
        return MIN_LENGTH(min).and(MAX_LENGTH(max));
    }
    /**
     * Returns a predicate that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}.
     *
     * @param <T> the type of arguments to the predicate
     * @param compared the object reference with which to compare for equality,
     *               which may be {@code null}
     * @return a predicate that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}
     */
    static <T> Predicate<T> EQUALS(T compared) {
       return (null == compared)
                ? Objects::isNull
                : compared::equals;
    }
    /**
     * Returns a predicate that tests if two arguments are NOT equal according
     * to {@link Objects#equals(Object, Object)}.
     *
     * @param <T> the type of arguments to the predicate
     * @param compared the object reference with which to compare for equality,
     *               which may be {@code null}
     * @return a predicate that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}
     */
    static <T> Predicate<T> NOT_EQUAL(T compared) {
        return (null == compared)
                ? Objects::nonNull
                : compared::equals;
    }


    static Predicate<Integer> LESS_THAN(int size) {
        return (value) -> value != null && value < size;
    }
    static Predicate<Integer> LESS_THAN_OR_EQUAL(int size) {
        return (value) -> value != null && value <= size;
    }
    static Predicate<Integer> GREATER_THAN(int size) {
        return (value) -> value != null && value > size;
    }
    static Predicate<Integer> GREATER_THAN_OR_EQUAL(int size) {
        return (value) -> value != null && value >= size;
    }

    static Predicate<String> MATCHES(String regex) {
        return str -> str!=null && str.matches(regex);
    }
    Predicate<String> IS_EMAIL = (email) -> email !=null && email.matches("^(.+)@(.+)$");

    static Predicate<Date> BEFORE_DATE(Date max) {
        return d -> d!=null && d.before(max);
    }
    static Predicate<Date> AFTER_DATE(Date min, Date max) {
        return d -> d!=null && d.after(min) ;
    }
//    between can include the endpoints , in which case you could
    static Predicate<Date> BETWEEN_DATES(Date min, Date max) {
        return d -> d!=null && !d.before(min) && !d.after(max);
    }



}
