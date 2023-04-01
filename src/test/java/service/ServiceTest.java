package service;

import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ServiceTest {

    private AutoCloseable closeable;

    @Mock
    private StudentXMLRepository studentXMLRepository;

    @Mock
    private TemaXMLRepository temaXMLRepository;

    @Mock
    private NotaXMLRepository notaXMLRepository;

    @InjectMocks
    private Service victim;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        victim = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void shouldAddStudent() {
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 1;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(student);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(0, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void shouldFailToAddStudent() {
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 1;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }
}