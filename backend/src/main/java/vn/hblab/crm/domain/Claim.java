package vn.hblab.crm.domain;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "claims")
public class Claim {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "observation_id") private Observation observation;
    @Column(nullable = false) private String summary;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private SignalType signalType;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Confidence confidence;
    @Lob @Column(nullable = false) private String quote;
    @Column(nullable = false) private int quoteStart;
    @Column(nullable = false) private int quoteEnd;
    @Column(nullable = false) private Instant createdAt = Instant.now();
    protected Claim() { }
}

