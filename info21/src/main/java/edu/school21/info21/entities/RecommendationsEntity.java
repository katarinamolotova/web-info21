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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecommendationsEntity that = (RecommendationsEntity) o;
        return id == that.id &&
               Objects.equals(peer, that.peer) &&
               Objects.equals(recommendedPeer, that.recommendedPeer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peer, recommendedPeer);
    }
}
