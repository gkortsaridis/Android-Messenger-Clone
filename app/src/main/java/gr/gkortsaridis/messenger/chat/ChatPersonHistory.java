package gr.gkortsaridis.messenger.chat;

import java.util.ArrayList;

/**
 * Created by yoko on 23/07/16.
 */
public class ChatPersonHistory {

    private String personId;
    private String personName;
    ArrayList<ChatMessage> messages;


    public ChatPersonHistory(String personId, String personName){
        this.personId = personId; this.personName = personName; messages = new ArrayList<>();
    }

    public String getPersonId() {
        return personId;
    }
    public void setPersonId(String personId) {
        this.personId = personId;
    }
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    public ArrayList<ChatMessage> getMessages(){ return messages; }
    public void setMessages(ArrayList<ChatMessage> messages){ this.messages = messages; }
    public void addChatMessage(ChatMessage chatMessage){
        messages.add(chatMessage);
    }
}
