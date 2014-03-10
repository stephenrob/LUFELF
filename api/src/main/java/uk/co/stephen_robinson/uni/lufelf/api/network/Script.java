package uk.co.stephen_robinson.uni.lufelf.api.network;

/**
 * Created by Stephen on 20/02/14.
 */

/**
 * @author stephen
 *
 *  Stores information regarding a script on the lufelf server
 */
public class Script {

    public enum Type {POST, GET}

    /**
     * Enum for storing the server protocol method
     */
    public enum Protocol {
        HTTP("http://"),
        HTTPS("https://");

        private final String value;

        /**
         * Sets the protocol data for the enum
         * @param newValue value to set the protocol to
         */
        private Protocol(final String newValue){
            value = newValue;
        }

        /**
         * returns the protocol value linked to the enum
         * @return protocol value
         */
        public String getProtocol(){
            return value;
        }
    }

    public final String path;
    public final Type method;
    public final Protocol protocol;
    public final String name;

    /**
     * Script constructor, used to set the script data
     *
     * @param name Name of the script
     * @param path Path on the server where the script is located, relative to the root IP/DNS address
     * @param protocol Protocol type this script requires, enum of type protocol required
     * @param method HTTP method this script uses either POST or GET, set via type enum
     */
    public Script(String name, String path, Protocol protocol, Type method){
        this.path = path;
        this.protocol = protocol;
        this.method = method;
        this.name = name;
    }

}
