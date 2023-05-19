package gb.library.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User extends IdBasedEntity{
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(length = 64) //длина закодированного пароля равна 64
    private String password;

    @Column
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", length = 10, nullable = false)
    private RegistrationType registrationType;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    /*
    * CONSTRUCTORS
     */

    /*
     * TRANSIENT FIELDS
     */
    @Transient
    public String getFullName(){
        return firstName + " " + lastName;
    }


    /*
    * OVERWRITE METHODS
     */
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }


    /*
    * METHODS
     */
    public void setFullName(String fullName){
        setFirstName(fullName.split(" ")[0]);
        setLastName(fullName.split(" ")[1]);
    }
}
