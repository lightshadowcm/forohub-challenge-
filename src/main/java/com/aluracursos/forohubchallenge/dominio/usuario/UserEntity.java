package com.aluracursos.forohubchallenge.dominio.usuario;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aluracursos.forohubchallenge.dominio.answer.Answer;
import com.aluracursos.forohubchallenge.dominio.perfil.Profile;
import com.aluracursos.forohubchallenge.dominio.topico.Topic;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_profile",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id")
    )
    private Set<Profile> profiles;

    @OneToMany(mappedBy = "author")
    private List<Answer> answers;

    @OneToMany(mappedBy = "author")
    private List<Topic> topics;

    public UserEntity(String name, String email, String password, Set<Profile> profile){
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = true;
        this.profiles = profile;
    }

    public void deactivateUser(){
        this.active = false;
    }

    public void updateUser(String nameIn, String emailIn, String passwordIn) {
        if(nameIn != null){
            this.name = nameIn;
        }

        if(emailIn != null){
            this.email = emailIn;
        }

        if(passwordIn != null){
            this.password = passwordIn;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        this.profiles.forEach(p -> {
            authorities.add(new SimpleGrantedAuthority(p.getName()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
        return this.active;
    }
}
