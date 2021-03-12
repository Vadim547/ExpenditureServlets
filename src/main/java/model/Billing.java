package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.profile.Fetch;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billings")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_sequence")
    @SequenceGenerator(
            name = "billing_sequence",
            sequenceName = "billing_sequence",
            allocationSize = 1
    )

    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", insertable = false, updatable = false)
    private User user;
    @Column(name = "user_id")
    private Long userId;

    private Double amount;
}
