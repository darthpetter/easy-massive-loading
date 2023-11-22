package io.github.darthpetter.domain.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify header information for Excel-related operations.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

    /**
     * Specifies the order of the header.
     *
     * @return The order of the header.
     */
    int order() default 0;

    /**
     * Specifies the label of the header. If empty, the field name is used as the
     * label.
     *
     * @return The label of the header.
     */
    String label() default "";
}
