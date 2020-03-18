package ro.uaic.info.exception;

public class InvalidCatalogException extends Exception{
    public InvalidCatalogException(Exception exception){
        super("Invalid catalog file.", exception);
    }
}
