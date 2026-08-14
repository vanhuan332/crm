package vn.hblab.crm.domain;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "observations")
public class Observation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "company_id") private Company company;
    @Column(nullable = false) private String sourceUrl;
    @Lob @Column(nullable = false) private String rawContent;
    @Column(nullable = false) private String normalizedContentHash;
    @Column(nullable = false) private Instant readAt = Instant.now();
    @Column(nullable = false) private boolean readable = true;
    protected Observation() { }
}

