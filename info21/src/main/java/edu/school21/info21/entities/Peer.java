package edu.school21.info21.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "peers")
public class Peer {
    @Id
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birthday")
    private LocalDate birthday;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(final LocalDate birthday) {
        this.birthday = birthday;
    }
}

