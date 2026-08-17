package com.pradnyasanskar.webstore.config;

import com.pradnyasanskar.webstore.security.CustomUserDetailsService;
import com.pradnyasanskar.webstore.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            CustomUserDetailsService customUserDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Stateless Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Authorization
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",

                                // Public authentication APIs
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/forgot-password",
                                "/api/auth/reset-password",

                                "/api/faqs/**"
                        ).permitAll()

                        // ==========================
                        // Authentication
                        // ==========================
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/test"
                        ).permitAll()

                        // ==========================
                        // Categories
                        // ==========================
                        .requestMatchers(HttpMethod.GET,
                                "/api/categories/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/categories/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/categories/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/categories/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Products
                        // ==========================
                        .requestMatchers(HttpMethod.GET,
                                "/api/products/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/products/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/products/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/products/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Product Variants
                        // ==========================
                        .requestMatchers(HttpMethod.GET,
                                "/api/product-variants/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/product-variants/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/product-variants/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/product-variants/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Inventory
                        // ==========================
                        .requestMatchers("/api/inventory-batches/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/stock-ledger/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Cart
                        // ==========================
                        .requestMatchers("/api/cart/**")
                        .hasRole("CUSTOMER")

                        // ==========================
                        // Wishlist
                        // ==========================
                        .requestMatchers("/api/wishlist/**")
                        .hasRole("CUSTOMER")

                        // ==========================
                        // Orders
                        // ==========================
                        .requestMatchers(HttpMethod.POST,
                                "/api/orders/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.GET,
                                "/api/orders/**")
                        .authenticated()

                        .requestMatchers(HttpMethod.PUT,
                                "/api/orders/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/orders/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Coupons
                        // ==========================
                        .requestMatchers(HttpMethod.GET,
                                "/api/coupons/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/coupons/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/coupons/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/coupons/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Reviews
                        // ==========================
                        .requestMatchers(HttpMethod.GET,
                                "/api/reviews/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/reviews/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/reviews/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/reviews/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Customer Modules
                        // ==========================
                        .requestMatchers(
                                "/api/addresses/**",
                                "/api/payments/**",
                                "/api/refunds/**",
                                "/api/shipments/**",
                                "/api/invoices/**",
                                "/api/return-requests/**",
                                "/api/user-consents/**"
                        ).authenticated()

                        // ==========================
                        // Dashboard
                        // ==========================
                        .requestMatchers("/api/dashboard/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Login History & Audit Logs
                        // ==========================
                        .requestMatchers(
                                "/api/login-history/**",
                                "/api/audit-logs/**"
                        ).hasRole("ADMIN")

                        // ==========================
                        // Blogs
                        // ==========================
                        .requestMatchers(HttpMethod.GET,
                                "/api/blogs/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/blogs/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/blogs/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/blogs/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // FAQs
                        // ==========================
                        .requestMatchers(HttpMethod.GET,
                                "/api/faqs/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/faqs/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/faqs/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/faqs/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Content Pages
                        // ==========================
                        .requestMatchers(HttpMethod.GET,
                                "/api/content-pages/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST,
                                "/api/content-pages/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/content-pages/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/content-pages/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // B2B Enquiries
                        // ==========================
                        .requestMatchers(HttpMethod.POST,
                                "/api/b2b-enquiries/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/api/b2b-enquiries/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Notifications
                        // ==========================
                        .requestMatchers("/api/notifications/**")
                        .authenticated()

                        // ==========================
                        // Users
                        // ==========================
                        .requestMatchers("/api/users/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Admin APIs
                        // ==========================
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        // ==========================
                        // Customer APIs
                        // ==========================
                        .requestMatchers("/api/customer/**")
                        .hasRole("CUSTOMER")

                        // ==========================
                        // Any Other API
                        // ==========================
                        .anyRequest()
                        .authenticated()
                )

                .userDetailsService(customUserDetailsService)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "http://localhost:3001",
                "http://localhost:3002"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}