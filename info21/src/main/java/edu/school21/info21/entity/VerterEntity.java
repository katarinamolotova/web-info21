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

import java.sql.Time;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "verter", schema = "public", catalog = "info21java")
public class VerterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "check_id")
    private int checkId;
    @Basic
    @Column(name = "state")
    private Object state;
    @Basic
    @Column(name = "verter_time")
    private Time verterTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VerterEntity that = (VerterEntity) o;
        return id == that.id &&
               checkId == that.checkId &&
               Objects.equals(state, that.state) &&
               Objects.equals(verterTime, that.verterTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkId, state, verterTime);
    }
}
