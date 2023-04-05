package service;

import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource({"buildValidStudentEcTests", "buildValidStudentBvaTests"})
    void shouldAddStudent(String id, String nume, int grupa) {
        // given
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(student);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(0, result);
        verify(studentXMLRepository).save(student);
    }

    @ParameterizedTest
    @MethodSource({"buildInvalidStudentEcTests", "buildInvalidStudentBvaTests"})
    void shouldFailToAddStudent(String id, String nume, int grupa) {
        // given
        Student student = new Student(id, nume, grupa);

        // when
        when(studentXMLRepository.save(any())).thenReturn(null);

        // then
        int result = victim.saveStudent(id, nume, grupa);

        assertEquals(1, result);
        verify(studentXMLRepository).save(student);
    }

    //EC test cases
    private static Stream<Arguments> buildInvalidStudentEcTests() {
        return Stream.of(
                Arguments.of(null, "nume", 936), // nullIdStudent
                Arguments.of("", "nume", 936), // emptyIdStudent
                Arguments.of("0", "nume", 110), // invalidGroupStudent
                Arguments.of("0", "nume", 938), // invalidGroup2Student
                Arguments.of("0", null, 933), //nullNameStudent
                Arguments.of("0", "", 933) //emptyNameStudent
                );
    }

    private static Stream<Arguments> buildValidStudentEcTests() {
        return Stream.of(Arguments.of("id", "nume", 111));
    }

    //BVA
    private static Stream<Arguments> buildInvalidStudentBvaTests() {
        return Stream.of(
                Arguments.of("id", "nume", 109), //groupLessThan110
                Arguments.of("id", "nume", 110), //groupEqualTo110
                Arguments.of("id", "nume", 938), //groupEqualTo938
                Arguments.of("id", "nume", 939) //groupGreaterThan938
        );
    }

    private static Stream<Arguments> buildValidStudentBvaTests() {
        return Stream.of(Arguments.of("id", "nume", 111), //groupGreaterThan110
                Arguments.of("id", "nume", 937) //groupLessThan938
        );
    }
}