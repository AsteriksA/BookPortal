package com.gold.security.jwt;

import com.gold.config.JwtSettings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenUtil implements Serializable {

    private static final String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";
    private static final long serialVersionUID = -3301605591108950415L;
    private Clock clock = DefaultClock.INSTANCE;

    private final JwtSettings jwtSettings;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        if (!RedisUtil.INSTANCE.sismember(REDIS_SET_ACTIVE_SUBJECTS, token)) {
            return null;
        }
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSettings.getTokenSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    @Async
    @Scheduled(fixedRate = 1000 * 10 * 60)
    public void removeExpiredToken() {
        RedisUtil.INSTANCE.smembers(REDIS_SET_ACTIVE_SUBJECTS);

        Set<String> tokens = RedisUtil.INSTANCE.smembers(REDIS_SET_ACTIVE_SUBJECTS);
        for (String token : tokens) {
            if (isTokenExpired(token)) {
                RedisUtil.INSTANCE.srem(REDIS_SET_ACTIVE_SUBJECTS, token);
            }
        }
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, jwtSettings.getTokenExpirationTime());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, jwtSettings.getRefreshTokenExpTime());
    }

    private String generateToken(UserDetails userDetails, Long expiration) {
        JwtUser jwtUser = (JwtUser) userDetails;
        Map<String, Object> claims = new HashMap<>();
        claims.put("scopes", jwtUser.getAuthorities().stream()
                .map(Object::toString).collect(Collectors.toList()));
        claims.put("userId", ((JwtUser) userDetails).getEntity().getId());
        return doGenerateToken(claims, userDetails.getUsername(), expiration);
    }

    public void removeToken(String token) {
        RedisUtil.INSTANCE.srem(REDIS_SET_ACTIVE_SUBJECTS, token);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, Long expiration) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate, expiration);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
                .compact();

        RedisUtil.INSTANCE.sadd(REDIS_SET_ACTIVE_SUBJECTS, token);
        return token;
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    Boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

    private Date calculateExpirationDate(Date createdDate, Long expiration) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
