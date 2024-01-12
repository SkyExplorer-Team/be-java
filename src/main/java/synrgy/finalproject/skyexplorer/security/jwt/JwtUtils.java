package synrgy.finalproject.skyexplorer.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import synrgy.finalproject.skyexplorer.security.service.UserDetailsImpl;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${skyexplorer.app.jwtSecret}")
    private String jwtSecret;

    @Value("${skyexplorer.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateToken(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders
                .BASE64URL.decode(jwtSecret));
    }

    public String getUsername(String jwt) {
        String username =  Jwts.parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
        return username;
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
