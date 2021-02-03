package ee.bcs.valiit.tasks.controller;


import ee.bcs.valiit.tasks.BankExceptions;
import ee.bcs.valiit.tasks.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BankControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BankExceptions.class)
    public ResponseEntity<ErrorMessage> handleBankExceptions(BankExceptions e) {
        ErrorMessage error = new ErrorMessage();
        error.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleExceptions(Exception e) {
        e.printStackTrace();
        ErrorMessage error = new ErrorMessage();
        error.setErrorMessage("Internal server error. Try again later.");
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
