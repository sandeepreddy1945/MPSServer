/**
 * 
 */
package com.mps.app.annotations;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Sandeep
 *
 */

@Documented
@Retention(RUNTIME)
@Target({METHOD, PACKAGE})
public @interface RestMethodAdvice {

}
