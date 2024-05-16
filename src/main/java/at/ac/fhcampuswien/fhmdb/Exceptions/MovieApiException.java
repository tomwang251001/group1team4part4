package at.ac.fhcampuswien.fhmdb.Exceptions;

public class MovieApiException extends Exception{
    public MovieApiException(){}
    public MovieApiException(String message, Throwable cause){
        super(message, cause);
    }
}
