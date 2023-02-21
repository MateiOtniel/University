package socialnetwork.repository.abstractrepo;

import socialnetwork.domain.Message;

import java.util.*;

public abstract class AbstractMessageRepository {
    protected List<Message> messageList;
    public AbstractMessageRepository(){
        messageList = new LinkedList<>();
    }

    public List<Message> getAll(){
        return this.messageList;
    }

    public abstract void save();

    public abstract void read();

    public void add(Message message) {
        messageList.add(message);
        save();
    }
}
