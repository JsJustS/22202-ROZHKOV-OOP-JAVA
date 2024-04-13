package task2.error;

public class BadArgumentCommandException extends CommandException {
    public BadArgumentCommandException(String message) {
        super(message);
    }
}
