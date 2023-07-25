package com.bitacademy.mysite.security;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME) // 지속시간
@Target(PARAMETER)	// 이게 뭐더라
public @interface AuthUser {
	
}
