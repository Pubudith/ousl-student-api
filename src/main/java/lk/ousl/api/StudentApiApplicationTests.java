package lk.ousl.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StudentApiApplicationTests {

	@Test
	void testStudentEntity() {
		Student s = new Student("S2201055", "Kasun Perera", "kasun@ousl.lk", "Software Engineering", 3.65);
		s.setId(1L);

		assertEquals(1L, s.getId());
		assertEquals("S2201055", s.getRegNo());
		assertEquals("Kasun Perera", s.getName());
		assertEquals("kasun@ousl.lk", s.getEmail());
		assertEquals("Software Engineering", s.getCourse());
		assertEquals(3.65, s.getGpa());
	}

	@Test
	void testControllerOperations() {
		StudentRepository mockRepo = Mockito.mock(StudentRepository.class);
		StudentController controller = new StudentController(mockRepo);

		Student s = new Student("S2201055", "Kasun Perera", "kasun@ousl.lk", "Software Engineering", 3.65);
		when(mockRepo.findAll()).thenReturn(List.of(s));
		when(mockRepo.findById(1L)).thenReturn(Optional.of(s));
		when(mockRepo.save(s)).thenReturn(s);
		when(mockRepo.existsById(1L)).thenReturn(true);

		assertEquals(1, controller.getAllStudents().size());
		assertEquals(HttpStatus.OK, controller.getStudentById(1L).getStatusCode());
		assertEquals(HttpStatus.NOT_FOUND, controller.getStudentById(99L).getStatusCode());

		Student created = controller.createStudent(s);
		assertEquals("Kasun Perera", created.getName());

		ResponseEntity<Student> updated = controller.updateStudent(1L, s);
		assertEquals(HttpStatus.OK, updated.getStatusCode());

		ResponseEntity<Void> deleted = controller.deleteStudent(1L);
		assertEquals(HttpStatus.NO_CONTENT, deleted.getStatusCode());
	}
}