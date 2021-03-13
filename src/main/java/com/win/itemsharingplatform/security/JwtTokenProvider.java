package com.win.itemsharingplatform.security;


import com.win.itemsharingplatform.exception.InvalidJwtAuthenticationException;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:900000}") // 15min sesijos trukme
    private long validityInMilliseconds;

    private final UserService userService;
    private final MessageSource messageSource;

    public JwtTokenProvider(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Optional<String> resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new InvalidJwtAuthenticationException(messageSource.getMessage("security.sessionExpired", null, null));
        }
    }

}
