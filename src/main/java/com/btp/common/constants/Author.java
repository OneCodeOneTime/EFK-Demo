package com.btp.common.constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法的作者
 * @author btp
 *
 */
@Target(value = { ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Author {
	String value();
}
