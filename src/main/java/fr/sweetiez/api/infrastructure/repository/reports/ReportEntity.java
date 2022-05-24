package fr.sweetiez.api.infrastructure.repository.reports;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "report")
public class ReportEntity {

    @Id
    @GeneratedValue
    private final UUID id;

    @Column(name = "reporter_id")
    private final UUID reporterId;

    @Column(name = "evaluation_id")
    private final UUID evaluationId;

    private final String reason;

    @CreationTimestamp
    private final LocalDateTime created;

    public ReportEntity() {
        id = null;
        reporterId = null;
        evaluationId = null;
        reason = null;
        created = null;
    }

    public ReportEntity(UUID id, UUID reporterId, UUID evaluationId, String reason, LocalDateTime created) {
        this.id = id;
        this.reporterId = reporterId;
        this.evaluationId = evaluationId;
        this.reason = reason;
        this.created = created;
    }

    public UUID getId() {
        return id;
    }

    public UUID getReporterId() {
        return reporterId;
    }

    public UUID getEvaluationId() {
        return evaluationId;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
