package com.example.acgdata.security;

import com.example.acgdata.common.Result;
import com.example.acgdata.common.ResultCode;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Spring Security 配置（无状态 JWT 鉴权）。
 *
 * <p>权限模型：
 * <ul>
 *     <li>公开：登录 / 注册、所有 GET 查询接口、全站搜索</li>
 *     <li>登录用户：点赞、评论、浏览计数、投稿</li>
 *     <li>管理员：修改、删除</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        // 登录 / 注册
                        .requestMatchers(HttpMethod.POST, "/api/user/login", "/api/user/register").permitAll()
                        // 管理员的用户列表
                        .requestMatchers(HttpMethod.GET, "/api/user/get/all").hasRole("ADMIN")
                        // 其余查询接口公开（访客可浏览）
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers("/api/search/**").permitAll()
                        // 互动行为需要登录
                        .requestMatchers(HttpMethod.POST,
                                "/api/*/like/**", "/api/*/dislike/**", "/api/*/addComment/**", "/api/*/view/**")
                        .authenticated()
                        // 投稿 / 新增需要登录
                        .requestMatchers(HttpMethod.POST, "/api/*/insert").authenticated()
                        // 修改 / 删除仅管理员
                        .requestMatchers(HttpMethod.PUT, "/api/*/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/*/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, e) ->
                                writeJson(response, HttpServletResponse.SC_UNAUTHORIZED, Result.fail(ResultCode.UNAUTHORIZED)))
                        .accessDeniedHandler((request, response, e) ->
                                writeJson(response, HttpServletResponse.SC_FORBIDDEN, Result.fail(ResultCode.FORBIDDEN))))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void writeJson(HttpServletResponse response, int status, Result<?> body) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
