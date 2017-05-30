package gr.gkortsaridis.messenger.chat;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by yoko on 23/07/16.
 */
public class ChatHistory {

    private ArrayList<ChatPersonHistory> chatPersonHistories;
    private String myName;

    public ChatHistory(){
        chatPersonHistories = new ArrayList<>();
    }

    public ArrayList<ChatPersonHistory> getChatPersonHistories() {
        return chatPersonHistories;
    }

    public void setChatPersonHistories(ArrayList<ChatPersonHistory> chatPersonHistories) {
        this.chatPersonHistories = chatPersonHistories;
    }

    public void addNewChatMessage(ChatMessage chatMessage, ChatPersonHistory chatPersonHistory){
        for(int i=0; i<chatPersonHistories.size(); i++){
            if(chatPersonHistories.get(i).getPersonId().equals(chatPersonHistory.getPersonId())){
                chatPersonHistories.get(i).addChatMessage(chatMessage);
            }
        }
    }

    public void saveChatHistory(Context context){
        final Gson gson = new Gson();
        String toSave = gson.toJson(this);
        SharedPreferences sharedpreferences = context.getSharedPreferences("chat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("chatHistory", toSave);
        editor.commit();
    }

    public ChatHistory loadChatHistory(Context context){
        final Gson gson = new Gson();
        SharedPreferences sharedpreferences = context.getSharedPreferences("chat", Context.MODE_PRIVATE);
        String json = sharedpreferences.getString("chatHistory","");
        if(json.equals("")){
            return null;
        }else {
            ChatHistory chatHistory = gson.fromJson(json, ChatHistory.class);
            return chatHistory;
        }
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }
}
