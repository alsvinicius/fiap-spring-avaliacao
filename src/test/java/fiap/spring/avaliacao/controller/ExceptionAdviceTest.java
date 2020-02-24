package fiap.spring.avaliacao.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionAdviceTest {

    ExceptionAdvice exceptionAdvice = new ExceptionAdvice();

    @Test
    public void handleException() {
        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exceptionAdvice.handleException(new RuntimeException(), null).getStatusCode()
        );
    }

    @Test
    public void handleEntityException() {
        assertEquals(
                HttpStatus.NOT_FOUND,
                exceptionAdvice.handleEntityException(new RuntimeException(), null).getStatusCode()
        );
    }

}
