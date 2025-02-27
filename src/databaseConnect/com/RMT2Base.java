package databaseConnect.com;

import java.util.Hashtable;

public abstract class RMT2Base {

    private static final String ENV = "Dev";
    /**
     * Message indicating that the logger has been initialized.
     */
    protected static final String LOGGER_INIT_MSG = "Logger has been initialized";

    /** Single messabe literal */
    public static final String SINGLE_MSG = "msg";

    /** Multi Message literal */
    public static final String MULTI_MSG = "messages";

    /** Success code */
    public static final int SUCCESS = 1;

    /** Failure code */
    public static final int FAILURE = -1;

    /** Messages hash */
    protected transient Hashtable<String, String> messages;

    /** Used to contain a single message */
    protected transient String msg;

    /**
     * Constructs a RMT2Base object
     * 
     */
    public RMT2Base() {
        this.init();
        return;
    }

    /**
     * Initializes the messages list.
     * 
     */
    public void init() {
        this.messages = new Hashtable<String, String>();
        return;
    }

    /**
     * Obtains the environment value.
     * 
     * @return String
     */
    public static final String getEnvironment() {
        return RMT2Base.ENV;
    }
}