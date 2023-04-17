package ru.vsu.cs.musiczoneserver.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import ru.vsu.cs.musiczoneserver.entity.model.Role;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "person")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String surname;
    @NonNull
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")
    private String email;
    @NonNull
    private String password;
    private String nickName;
    private String phone;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<GrantedAuthority> roles;

    @ManyToMany(mappedBy = "people")
    private Set<Playlist> playlists;
}
