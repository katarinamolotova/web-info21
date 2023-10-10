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
@Table(name = "tasks", schema = "public", catalog = "info21java")
public class TasksEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "parent_task")
    private String parentTask;
    @Basic
    @Column(name = "max_xp")
    private int maxXp;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TasksEntity that = (TasksEntity) o;
        return maxXp == that.maxXp &&
               Objects.equals(title, that.title) &&
               Objects.equals(parentTask, that.parentTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, parentTask, maxXp);
    }
}
