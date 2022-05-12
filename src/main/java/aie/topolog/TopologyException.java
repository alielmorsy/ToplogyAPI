package aie.topolog;

/**
 * A Exception Required for cases like topology not in list or something wrong happened while parsing a topology
 */
public class TopologyException extends Exception {

    public TopologyException() {
    }

    public TopologyException(String message) {
        super(message);
    }

    public TopologyException(String message, Throwable cause) {
        super(message, cause);
    }
}
