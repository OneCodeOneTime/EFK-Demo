package com.btp.common.constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����������
 * @author btp
 *
 */
@Target(value = { ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Author {
	String value();
}
