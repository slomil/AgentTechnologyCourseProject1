package JmsMessages;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author - Srđan Milaković
 */
public class JmsMessage implements Serializable {
    protected UUID uuid;

    public JmsMessage() {
        uuid = UUID.randomUUID();
    }
}
