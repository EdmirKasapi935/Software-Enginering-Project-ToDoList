package Handlers;

import CustomExcptions.EmptyInputException;

public class Validator {

    public String validateNotNull(String input, String message) throws EmptyInputException
    {
        if(input.trim().equals(""))
        {
            throw new EmptyInputException(message);
        }

        return input;
    }

}
