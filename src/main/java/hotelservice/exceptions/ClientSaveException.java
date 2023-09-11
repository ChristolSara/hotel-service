package hotelservice.exceptions;

import org.springframework.dao.DataAccessException;

public class ClientSaveException extends Exception {
    public ClientSaveException(String s, DataAccessException ex) {

    }
}
