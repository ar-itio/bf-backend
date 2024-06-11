package com.onlinebankingsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.onlinebankingsystem.filter.JwtAuthFilter;
import com.onlinebankingsystem.utility.Constants.UserRole;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	// authentication
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())

				.authorizeHttpRequests(
						auth -> auth
								.requestMatchers("/api/user/tfa", "/api/user/verify", "/api/user/login",
										"/api/user/admin/register", "/api/user/register",
										"/api/transaction/update/status", "/api/user/fetch/userId",
										"/api/transaction/fetch/customer/transactions/all", "/api/user/fetch/id",
										"/api/user/send/reset-password/mail", "/api/user/addAccount",
										"/api/user/deleteAccount/acno",
										"/api/user/reset-password", "/api/user/update-profile",
										"/api/user/upload-profile-image", "/api/fee/detail/fetch/type",
										"/api/currencies/add", "/api/currencies/fatch", "/api/currencies/fatchAccount",
										"/api/currencies/fatchHostDetail", "/api/currencies/delete/id",
										"/api/currencies/deleteAccount/id",
										"/api/currencies/updateHostDetail", "/api/currencies/fatchHostDetail",
										"/api/currencies/fatchAdminAccount")
								.permitAll()

								// this APIs are only accessible by ADMIN
								.requestMatchers("/api/bank/register", "/api/bank/fetch/all", "/api/bank/fetch/user",
										"/api/bank/account/fetch/all", "/api/bank/transaction/all",
										"/api/currencies/add")
								.hasAuthority(UserRole.ROLE_ADMIN.value())

								// this APIs are only accessible by BANK
								.requestMatchers("/api/bank/account/add", "/api/bank/account/fetch/bankwise",
										"/api/bank/account/fetch/id", "/api/bank/account/search",
										"/api/bank/transaction/deposit", "/api/bank/transaction/withdraw",
										"/api/bank/transaction/customer/fetch",
										"/api/bank/transaction/customer/fetch/timerange",
										"/api/bank/transaction/all/customer/fetch/timerange",
										"/api/bank/transaction/all/customer/fetch",
										"/api/user/bank/customer/search")
								.hasAuthority(UserRole.ROLE_BANK.value())

								// this APIs are only accessible by CUSTOMER
								.requestMatchers("/api/bank/transaction/account/transfer",
										"/api/bank/transaction/history/timerange", "/api/transaction/addMoney")
								.hasAuthority(UserRole.ROLE_CUSTOMER.value())

								// this APIs are only accessible by BANK & CUSTOMER
								.requestMatchers("/api/bank/account/fetch/user", "/api/bank/transaction/history",
										"/api/user/update-profile")
								.hasAnyAuthority(UserRole.ROLE_BANK.value(), UserRole.ROLE_CUSTOMER.value(),
										UserRole.ROLE_ADMIN.value())

								// this APIs are only accessible by BANK & ADMIN
								.requestMatchers("/api/bank/account/search/all")
								.hasAnyAuthority(UserRole.ROLE_BANK.value(), UserRole.ROLE_ADMIN.value())

								// this APIs are only accessible by BANK, ADMIN & CUSTOMER
								.requestMatchers("/api/bank/fetch/id", "/api/bank/transaction/statement/download")
								.hasAnyAuthority(UserRole.ROLE_BANK.value(), UserRole.ROLE_ADMIN.value(),
										UserRole.ROLE_CUSTOMER.value())

								.anyRequest()
								.authenticated())

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}