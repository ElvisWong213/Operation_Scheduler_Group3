package backend.dataStructure.exception;

/**
 * DuplicateElementException is an exception that is thrown when an element already exists
 * in a collection where duplicates aren't allowed.
 */
public class DuplicateElementException extends Exception {
  
    /**
     * Constructs a new DuplicateElementException with the specified detail message.
     * @param message the detail message (which is saved for later retrieval by the 
     *                {@link Throwable#getMessage()} method)
     */
    public DuplicateElementException(String message) {
        super(message);
    }
}