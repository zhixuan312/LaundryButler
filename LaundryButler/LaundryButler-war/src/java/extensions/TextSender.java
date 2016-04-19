package extensions;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author HoRenSen
 */
public class TextSender {

    private static final String ACCOUNT_SID = "AC41c9a95bc6523553b2bef85e4d631689";
    private static final String AUTH_TOKEN = "e0f95c1a8976f0587cfe8aa5c3e259fe";
    private static final String SENDER_PHONE_NUMBER = "+14155296748";
    private String recipientPhoneNumber;
    private String bodyMessage;

    public TextSender() {
    }

    public void sendText() throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("To", recipientPhoneNumber));
        params.add(new BasicNameValuePair("From", SENDER_PHONE_NUMBER));
        params.add(new BasicNameValuePair("Body", bodyMessage));
        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = messageFactory.create(params);
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

}
