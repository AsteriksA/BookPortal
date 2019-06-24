package com.gold.security2.jwt;

import com.gold.config.JwtSettings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenUtil implements Serializable {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "iat";
    private static final long serialVersionUID = -3301605591108950415L;
//    @SuppressFBWarnings(value = "SE_BAD_FIELD", justification = "It's okay here")
    private Clock clock = DefaultClock.INSTANCE;

    private final JwtSettings jwtSettings;

    @Value("${demo.security.jwt.tokenSigningKey}")
    private String secret;

    @Value("${demo.security.jwt.tokenExpirationTime}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }



    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateAccesToken(UserDetails userDetails) {
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
        return doGenerateToken(claims, userDetails.getUsername(), expiration);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, Long expiration) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate, expiration);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

//    public Boolean canTokenBeRefreshed(String token) {
//        final Date created = getIssuedAtDateFromToken(token);
//        return !isTokenExpired(token);
//    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
            && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token, Long expiration) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate, expiration);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        JwtUser user = (JwtUser) userDetails;
//        final String username = getUsernameFromToken(token);
//        final Date created = getIssuedAtDateFromToken(token);
//        //final Date expiration = getExpirationDateFromToken(token);
//        return (
//            username.equals(user.getUsername())
//                && !isTokenExpired(token)
//                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
//        );
//    }


    public Boolean validateToken(String token) {
        /*final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);*/
        return (!isTokenExpired(token));
    }

    private Date calculateExpirationDate(Date createdDate, Long expiration) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
