package shakkiturnaus.Model;

/**
 * @author lehti
 * @version 1.3.2021
 *
 */
public class PoikkeusException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param e virhe
     */
    public PoikkeusException(String e) {
        super(e);
    }

}
