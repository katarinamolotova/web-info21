package edu.school21.info21.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@Table(name = "time_tracking", schema = "public", catalog = "info21java")
public class TimeTrackingEntity implements EntityInfo {

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
}
