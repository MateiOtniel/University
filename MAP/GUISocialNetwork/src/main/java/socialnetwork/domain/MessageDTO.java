package socialnetwork.domain;

public class MessageDTO {
    private final String messageFrom;
    private final String messageTo;

    public MessageDTO(String messageFrom, String messageTo) {
        this.messageFrom = messageFrom;
        this.messageTo = messageTo;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public String getMessageTo() {
        return messageTo;
    }
}
