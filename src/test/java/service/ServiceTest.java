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

    //EC test cases
    @Test
    void nullIdStudent(){
        // given
        Student student = new Student(null, "nume", 938);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent(null, "nume", 938);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void idNotUnique(){
        // given
        Student student = new Student("1", "nume", 938);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent("1", "nume", 938);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }


    @Test
    void emptyIdStudent(){
        // given
        Student student = new Student("", "nume", 938);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent("", "nume", 938);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    //group<=110
    @Test
    void invalidGroupStudent(){
        // given
        Student student = new Student("0", "nume", 110);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent("0", "nume", 110);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    //group>=938
    @Test
    void invalidGroup2Student(){
        // given
        Student student = new Student("0", "nume", 938);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent("0", "nume", 938);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void nullNameStudent(){
        // given
        Student student = new Student("0", null, 933);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent("0", null, 933);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void emptyNameStudent(){
        // given
        Student student = new Student("0", "", 933);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent("0", "", 933);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }
    //BVA
    @Test
    void groupLessThan110(){
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 109;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void groupEqualTo110(){
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 110;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void groupGreaterThan110(){
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 111;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(student);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(0, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void groupLessThan938(){
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 937;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(student);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(0, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void groupEqualTo938(){
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 938;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    @Test
    void groupGreaterThan938(){
        // given
        var id = "id";
        var nume = "nume";
        var grupa = 939;
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }



}