package ua.tunepoint.account.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Profile profile;

    @OneToOne
    @PrimaryKeyJoinColumn
    private UserStatistics statistics;

    public static User create(Long id, String username, String email) {
        var user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);

        return user;
    }
}
