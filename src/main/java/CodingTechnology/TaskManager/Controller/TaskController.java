package CodingTechnology.TaskManager.Controller; // Seu novo pacote para o Controller

import CodingTechnology.TaskManager.Model.Task; // Seu novo pacote para o Model
import CodingTechnology.TaskManager.Repository.TaskRepository; // Seu novo pacote para o Repository e nome da interface (com 'T' maiúsculo)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task") // Sua nova URL base para a API
public class TaskController {

    @Autowired
    private TaskRepository taskRepository; // Usando o nome da interface TaskRepository

    // Endpoint para obter todas as tarefas
    @GetMapping("/getAll") // Sua nova URL para listar todas as tarefas
    public List<Task> getAllTask(){ // Seu novo nome de método
        return taskRepository.findAll();
    }

    // Endpoint para criar uma nova tarefa
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Reintroduzido para boas práticas REST (status 201 Created)
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }
    
    // Endpoint para buscar uma tarefa específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para atualizar uma tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()){
            Task existingTask = task.get();
            existingTask.setTitle(taskDetails.getTitle());
            existingTask.setDescription(taskDetails.getDescription());
            existingTask.setCompleted(taskDetails.isCompleted());
            return ResponseEntity.ok(taskRepository.save(existingTask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para deletar uma tarefa
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } 
        else { 
            return ResponseEntity.notFound().build(); 
        }
    }
}