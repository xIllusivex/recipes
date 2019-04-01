package app.recipes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataTypeException extends NumberFormatException
{
    public DataTypeException()
    {
    }

    public DataTypeException(String s)
    {
        super(s);
    }
}
