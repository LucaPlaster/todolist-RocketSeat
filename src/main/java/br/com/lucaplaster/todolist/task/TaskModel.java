package br.com.lucaplaster.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "TB_TASKS")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    private String description;
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50){
            throw new Exception("O título deve ser menor do que 50 caracteres");
        }
        this.title = title;
    }
}
