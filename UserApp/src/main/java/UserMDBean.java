import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author - Srđan Milaković
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/topic/UserApp")
        }
)
public class UserMDBean implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(message);
    }
}
