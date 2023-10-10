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
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "checks", schema = "public", catalog = "info21java")
public class ChecksEntity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChecksEntity that = (ChecksEntity) o;
        return id == that.id &&
               Objects.equals(peer, that.peer) &&
               Objects.equals(task, that.task) &&
               Objects.equals(checkDate, that.checkDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peer, task, checkDate);
    }
}
