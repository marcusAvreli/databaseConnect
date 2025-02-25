package databaseConnect.api.persistence;

public class CannotReadManagerConfigurationException extends DatabaseException {

    private static final long serialVersionUID = 5849320232601197106L;

    public CannotReadManagerConfigurationException(String msg) {
        super(msg);
    }

    public CannotReadManagerConfigurationException(Exception e) {
        super(e);
    }

    public CannotReadManagerConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
