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

import java.sql.Time;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "p2p", schema = "public", catalog = "info21java")
public class P2PEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "check_id")
    private int checkId;
    @Basic
    @Column(name = "checking_peer")
    private String checkingPeer;
    @Basic
    @Column(name = "state")
    private Object state;
    @Basic
    @Column(name = "check_time")
    private Time checkTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        P2PEntity p2PEntity = (P2PEntity) o;
        return id == p2PEntity.id &&
               checkId == p2PEntity.checkId &&
               Objects.equals(checkingPeer, p2PEntity.checkingPeer) &&
               Objects.equals(state, p2PEntity.state) &&
               Objects.equals(checkTime, p2PEntity.checkTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkId, checkingPeer, state, checkTime);
    }
}
