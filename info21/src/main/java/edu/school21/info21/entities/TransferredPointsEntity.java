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
@Table(name = "transferred_points", schema = "public", catalog = "info21java")
public class TransferredPointsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "checking_peer")
    private String checkingPeer;
    @Basic
    @Column(name = "checked_peer")
    private String checkedPeer;
    @Basic
    @Column(name = "points_amount")
    private int pointsAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransferredPointsEntity that = (TransferredPointsEntity) o;
        return id == that.id &&
               pointsAmount == that.pointsAmount &&
               Objects.equals(checkingPeer, that.checkingPeer) &&
               Objects.equals(checkedPeer, that.checkedPeer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkingPeer, checkedPeer, pointsAmount);
    }
}
