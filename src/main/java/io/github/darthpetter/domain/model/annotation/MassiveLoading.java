package io.github.darthpetter.domain.model.annotation;

/**
 * Annotation to indicate that a class is designed for massive loading
 * operations.
 */
public @interface MassiveLoading {

    /**
     * Specifies the number of rows to be processed during massive loading.
     *
     * @return The number of rows to be processed.
     */
    int numberRow() default 0;
}
