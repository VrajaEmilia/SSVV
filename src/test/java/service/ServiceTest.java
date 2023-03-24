package service;

import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ServiceTest  {

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
        initMocks(this);
        victim = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @Test
    public void shouldAddStudent() {
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 1;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(student);

        // actual
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(0, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    public void shouldFailToAddStudent() {
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 1;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // actual
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }
}