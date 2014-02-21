package uk.co.stephen_robinson.uni.lufelf.api.network;

/**
 * Created by Stephen on 20/02/14.
 */
public class Script {

    public enum Type {POST, GET}

    public enum Protocol {
        HTTP("http://"),
        HTTPS("https://");

        private final String value;

        private Protocol(final String newValue){
            value = newValue;
        }

        public String getProtocol(){
            return value;
        }
    }

    public final String path;
    public final Type method;
    public final Protocol protocol;
    public final String name;

    public Script(String name, String path, Protocol protocol, Type method){
        this.path = path;
        this.protocol = protocol;
        this.method = method;
        this.name = name;
    }

}
