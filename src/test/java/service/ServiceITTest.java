package service;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServiceITTest extends ServiceTest {

    private static final String ID_TEMA = "idTema";
    private static final String DESC = "desc";
    private static final int DEADLINE = 10;
    private static final int STARTLINE = 5;
    private static final String ID_STUDENT = "idStudent";
    private static final double VAL_NOTA = 4.5;
    private static final int PREDATA = 4;
    private static final String FEEDBACK = "feedback";
    private static final String NUME = "nume";
    private static final int GRUPA = 935;

    @Test
    void shouldAddAssignment() {
        Tema tema = new Tema(ID_TEMA, DESC, DEADLINE, STARTLINE);

        when(temaXMLRepository.save(any())).thenReturn(tema);

        int result = victim.saveTema(ID_TEMA, DESC, DEADLINE, STARTLINE);

        assertEquals(0, result);
    }

    @Test
    void shouldAddGrade() {
        Student student = new Student(ID_STUDENT, NUME, GRUPA);
        Tema tema = new Tema(ID_TEMA, DESC, 10, 2);
        Nota nota = new Nota(new Pair<>(ID_STUDENT, ID_TEMA), VAL_NOTA, PREDATA, FEEDBACK);

        when(studentXMLRepository.findOne(any())).thenReturn(student);
        when(temaXMLRepository.findOne(any())).thenReturn(tema);
        when(notaXMLRepository.save(any())).thenReturn(nota);

        int result = victim.saveNota(ID_STUDENT, ID_TEMA, VAL_NOTA, PREDATA, FEEDBACK);

        assertEquals(0, result);
    }

    @Test
    void shouldTestEntireFlow() {
        Student student = new Student(ID_STUDENT, NUME, GRUPA);
        Tema tema = new Tema(ID_TEMA, DESC, DEADLINE, STARTLINE);
        Nota nota = new Nota(new Pair<>(ID_STUDENT, ID_TEMA), VAL_NOTA, PREDATA, FEEDBACK);

        when(studentXMLRepository.save(any())).thenReturn(student);
        when(temaXMLRepository.save(any())).thenReturn(tema);
        when(studentXMLRepository.findOne(any())).thenReturn(student);
        when(temaXMLRepository.findOne(any())).thenReturn(tema);
        when(notaXMLRepository.save(any())).thenReturn(nota);

        int resultStudent = victim.saveStudent(ID_STUDENT, NUME, GRUPA);
        int resultTema = victim.saveTema(ID_TEMA, DESC, DEADLINE, STARTLINE);
        int resultNota = victim.saveNota(ID_STUDENT, ID_TEMA, VAL_NOTA, PREDATA, FEEDBACK);

        assertEquals(0, resultStudent);
        assertEquals(0, resultTema);
        assertEquals(0, resultNota);
    }
}
