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

@Entity
@Data
@Table(name = "checks", schema = "public", catalog = "info21java")
public class CheckEntity implements EntityInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "peer")
    private String peer;
    @Basic
    @Column(name = "task")
    private String task;
    @Basic
    @Column(name = "check_date")
    private Date checkDate;
}
