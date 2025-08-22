package CodingTechnology.TaskManager.Repository;

import CodingTechnology.TaskManager.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface taskRepository extends JpaRepository<Task, Long> {
}