//package com.example.config;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//import java.util.regex.Pattern;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Value("${swagger.username}")
//    private String username;
//    @Value("${swagger.password}")
//    private String password;
//
//    private static final String NOOP_PASSWORD_PREFIX = "{noop}";
//    private static final Pattern PASSWORD_ALGORITHM_PATTERN = Pattern.compile("^\\{.+}.*$");
//    private static final Log logger = LogFactory.getLog(SecurityConfig.class);
//
//
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .authorizeExchange()
//                .pathMatchers("/swagger-ui/index.html").authenticated()
//                .pathMatchers("/swagger-resources/**").authenticated()
//                .pathMatchers("/v2/api-docs").authenticated()
//                .pathMatchers("/v3/api-docs").authenticated()
//                .pathMatchers("/webjars/**").authenticated()
//                .pathMatchers("/actuator/**").authenticated()
//                .pathMatchers("/password").authenticated()
//                .anyExchange().permitAll()
//                .and()
//                .formLogin()
//                .and()
//                .logout()
//                .and()
//                .csrf().disable()
//                .build();
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername(username)
//                .password(password)
//                .roles("USER")
//                .build();
//        return new MapReactiveUserDetailsService(user);
//    }
//    @Bean
//    @Lazy
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager(SecurityProperties properties, ObjectProvider<PasswordEncoder> passwordEncoder) {
//        SecurityProperties.User user = properties.getUser();
//        user.setName(username);
//        user.setPassword(this.passwordEncoder().encode(password));
//        List<String> roles = user.getRoles();
//        return new InMemoryUserDetailsManager(new UserDetails[]{User.withUsername(user.getName()).password(getOrDeducePassword(user, (PasswordEncoder)passwordEncoder.getIfAvailable())).roles(StringUtils.toStringArray(roles)).build()});
//    }
//    private String getOrDeducePassword(SecurityProperties.User user, PasswordEncoder encoder) {
//        String password = user.getPassword();
//        if (user.isPasswordGenerated()) {
//            logger.warn(String.format("%n%nUsing generated security password: %s%n%nThis generated password is for development use only. Your security configuration must be updated before running your application in production.%n", user.getPassword()));
//        }
//
//        return encoder == null && !PASSWORD_ALGORITHM_PATTERN.matcher(password).matches() ? "{noop}" + password : password;
//    }
//}
