package ro.uaic.info.utility;

public interface Command {
    Object execute();
    boolean hasResult();
}
