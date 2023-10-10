package edu.school21.info21.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "xp", schema = "public", catalog = "info21java")
public class XpEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "check_id")
    private int checkId;
    @Basic
    @Column(name = "xp_amount")
    private int xpAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        XpEntity xpEntity = (XpEntity) o;
        return id == xpEntity.id && checkId == xpEntity.checkId && xpAmount == xpEntity.xpAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkId, xpAmount);
    }
}
