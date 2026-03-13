package CustomExceptions;

public class TaskTextLengthExceededException extends Exception {

    public TaskTextLengthExceededException(String message)
    {
        super(message);
    }

}
