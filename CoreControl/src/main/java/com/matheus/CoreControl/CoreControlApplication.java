package com.matheus.CoreControl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.matheus.CoreControl.configurations.AuthenticationFilter;

@SpringBootApplication
public class CoreControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreControlApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthenticationFilter> loggingFilter() {
		FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new AuthenticationFilter());
		registrationBean.addUrlPatterns("/home/*", "/produtos/*", "/reports/*", "/users/*", "/"); // Add URL
		// patterns to
		// protect

		return registrationBean;
	}
}
