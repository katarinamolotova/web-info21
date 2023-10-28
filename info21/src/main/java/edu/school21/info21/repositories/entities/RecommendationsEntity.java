package edu.school21.info21.repositories.entities;

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
@Table(name = "recommendations", schema = "public", catalog = "info21java")
public class RecommendationsEntity implements EntityInfo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "peer")
    private String peer;

    @Basic
    @Column(name = "recommended_peer")
    private String recommendedPeer;
}
