package edu.school21.info21.repositories.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tasks", schema = "public", catalog = "info21java")
public class TaskEntity implements EntityInfo {

    @Id
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "parent_task")
    private String parentTask;

    @Basic
    @Column(name = "max_xp")
    private int maxXp;
}
