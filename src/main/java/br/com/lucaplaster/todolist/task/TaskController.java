package br.com.lucaplaster.todolist.task;

import br.com.lucaplaster.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        var currentDateTime = LocalDateTime.now();

        if((currentDateTime.isAfter(taskModel.getStartAt())) || (currentDateTime.isAfter(taskModel.getEndAt()))){
            return ResponseEntity.badRequest().body("A data de início/final deve ser maior do que a data atual");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.badRequest().body("A data de inicio deve ser menor do que a data de término");
        }

        TaskModel savedTask = this.taskRepository.save(taskModel);
        return ResponseEntity.ok().body(savedTask);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        return this.taskRepository.findByUserId((UUID) userId);
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel updatedTask, @PathVariable UUID id, HttpServletRequest request){
        var task = this.taskRepository.findById(id).orElse(null);

        if (task == null){
            return ResponseEntity.badRequest().body("Tarefa não encontrada");
        }

        var userId = request.getAttribute("userId");

        if(!task.getUserId().equals(userId)){
            return ResponseEntity.badRequest().body("Usuário não tem permissão para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(updatedTask, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
