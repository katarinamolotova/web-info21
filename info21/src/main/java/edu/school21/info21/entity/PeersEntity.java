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
@Table(name = "peers", schema = "public", catalog = "info21java")
public class PeersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "nickname")
    private String nickname;
    @Basic
    @Column(name = "birthday")
    private Date birthday;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PeersEntity that = (PeersEntity) o;
        return Objects.equals(nickname, that.nickname) && Objects.equals(birthday, that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, birthday);
    }
}
