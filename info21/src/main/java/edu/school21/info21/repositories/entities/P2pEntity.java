package edu.school21.info21.repositories.entities;

import edu.school21.info21.enums.CheckState;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Time;

@Entity
@Data
@Table(name = "p2p", schema = "public", catalog = "info21java")
public class P2pEntity implements EntityInfo {

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
    @Enumerated(EnumType.STRING)
    private CheckState state;

    @Basic
    @Column(name = "check_time")
    private Time checkTime;
}
