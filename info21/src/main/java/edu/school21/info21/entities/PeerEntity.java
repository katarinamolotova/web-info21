package edu.school21.info21.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "peers", schema = "public", catalog = "info21java")
public class PeerEntity {
    @Id
    @Column(name = "nickname")
    private String nickname;
    @Basic
    @Column(name = "birthday")
    private Date birthday;
}
