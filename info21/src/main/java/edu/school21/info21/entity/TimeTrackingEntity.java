package edu.school21.info21.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "time_tracking", schema = "public", catalog = "info21java")
public class TimeTrackingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "peer")
    private String peer;
    @Basic
    @Column(name = "visit_date")
    private Date visitDate;
    @Basic
    @Column(name = "visit_time")
    private Time visitTime;
    @Basic
    @Column(name = "state")
    private int state;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeTrackingEntity that = (TimeTrackingEntity) o;
        return id == that.id &&
               state == that.state &&
               Objects.equals(peer, that.peer) &&
               Objects.equals(visitDate, that.visitDate) &&
               Objects.equals(visitTime, that.visitTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peer, visitDate, visitTime, state);
    }
}
