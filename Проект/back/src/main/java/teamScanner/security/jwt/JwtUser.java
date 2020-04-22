package teamScanner.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;


public class JwtUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String city;
    private final Date age;
    private final String password;
//    private final String email;
    private final boolean enabled;
    private final boolean banned;
    private final Date lastPasswordResetDate;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            Long id,
            String username,
            String city,
            Date age,
//            String email,
            String password, Collection<? extends GrantedAuthority> authorities,
            boolean enabled,
            boolean banned,
            Date lastPasswordResetDate
    ) {
        this.id = id;
        this.username = username;
        this.city = city;
        this.age = age;
//        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.banned = banned;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

//    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !banned;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getCity() {
        return city;
    }

    public Date getAge() {
        return age;
    }

    //    public String getFirstname() {
//        return city;
//    }

//    public String getLastname() {
//        return lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}
