package ua.tunepoint.account.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "confirmation_codes")
public class ConfirmationCode {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "code")
    private String code;

    @Column(name = "attempts")
    private Integer attempts;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "last_sent")
    private LocalDateTime lastSent;

    public void reset(String code, Duration due) {
        var now = LocalDateTime.now();

        this.code = code;
        this.attempts = 0;
        this.dueDate = now.plus(due);
        this.lastSent = now;
    }

    public void incrementAttempts() {
        setAttempts(this.attempts + 1);
    }

    public static ConfirmationCode create(Long id, String code, Duration due) {
        var codeEntity = new ConfirmationCode();
        var now = LocalDateTime.now();

        codeEntity.setId(id);
        codeEntity.setCode(code);
        codeEntity.setDueDate(now.plus(due));
        codeEntity.setLastSent(now);
        codeEntity.setAttempts(0);

        return codeEntity;
    }
}
