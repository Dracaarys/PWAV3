package av3.pw.ufrn.domain;

import av3.pw.ufrn.domain.generic.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Entity(name = "security_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SQLDelete(sql = "UPDATE security_user SET deleted_at = CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at is null")

public class SecurityUser extends AbstractEntity implements UserDetails{
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "pessoa_id")
    private Cliente cliente;

    @Column(unique = true)
    private String email;

    @Column
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.cliente.isAdmin){
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
