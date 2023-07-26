package com.bitacademy.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 최상위 패키지에 이걸 써주면 그 이하를 쫙 자동스캔한다.
public class MySiteApplication {
	public static void main(String[] args) {
		SpringApplication.run(MySiteApplication.class, args);
	}
}
