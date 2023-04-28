package validation;

import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemaValidatorTest {

    private TemaValidator victim;

    @BeforeEach
    void setUp() {
        victim =  new TemaValidator();
    }

    @Test
    @DisplayName("Should Throw Validation Exception When Id Is Null")
    void shouldThrowValidationExceptionWhenIdIsNull() {
        Tema tema = new Tema(null, "hey", 11, 9);

        ValidationException exception = assertThrows(ValidationException.class, () -> victim.validate(tema));
        assertEquals("ID invalid! \n", exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw Validation Exception When Description Is Null")
    void shouldThrowValidationExceptionWhenDescriptionIsNull() {
        Tema tema = new Tema("1", null, 11, 9);

        ValidationException exception = assertThrows(ValidationException.class, () -> victim.validate(tema));
        assertEquals("Descriere invalida! \n", exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw Validation Exception When Deadline Is Less Then One")
    void shouldThrowValidationExceptionWhenDeadlineIsLessThenOne() {
        Tema tema = new Tema("1", "hey", -11, 9);

        ValidationException exception = assertThrows(ValidationException.class, () -> victim.validate(tema));
        assertEquals("Deadline invalid! \n", exception.getMessage());
    }

    @Test
    @DisplayName("Should Throw Validation Exception When Startline Is Less Then One")
    void shouldThrowValidationExceptionWhenStartlineIsLessThenOne() {
        Tema tema = new Tema("1", "hey", 11, 0);

        ValidationException exception = assertThrows(ValidationException.class, () -> victim.validate(tema));
        assertEquals("Data de primire invalida! \n", exception.getMessage());
    }

    @Test
    @DisplayName("Should Not Throw Any Exceptions")
    void shouldNotThrowAnyExceptions() {
        Tema tema = new Tema("1", "hey", 11, 8);

        assertDoesNotThrow(() -> victim.validate(tema));
    }
}