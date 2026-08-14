package vn.hblab.crm.domain;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "proposals")
public class Proposal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "claim_id") private Claim claim;
    @Column(nullable = false) private String currentValue;
    @Column(nullable = false) private String proposedValue;
    @Column(nullable = false) private String riskIfWrong;
    @Column(nullable = false) private Instant createdAt = Instant.now();
    protected Proposal() { }
}

