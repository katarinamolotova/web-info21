package edu.school21.info21.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "transferred_points", schema = "public", catalog = "info21java")
public class TransferredPointsEntity implements EntityInfo {
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
}
