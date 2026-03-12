package Handlers;

import CustomExceptions.EmptyInputException;
import CustomExceptions.ListNameLengthExceededException;

public class Validator {

    public String validateNotNull(String input, String message) throws EmptyInputException
    {
        if(input.trim().equals(""))
        {
            throw new EmptyInputException(message);
        }

        return input;
    }

    public String validateListNameLength(String listName) throws ListNameLengthExceededException
    {
        if (listName.length() > 40)
        {
            throw new ListNameLengthExceededException("Name of a list cannot exceed 40 characters!");
        }

        return listName;
    }

}
