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

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "friends", schema = "public", catalog = "info21java")
public class FriendsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "peer1")
    private String peer1;
    @Basic
    @Column(name = "peer2")
    private String peer2;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FriendsEntity that = (FriendsEntity) o;
        return id == that.id && Objects.equals(peer1, that.peer1) && Objects.equals(peer2, that.peer2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peer1, peer2);
    }
}
