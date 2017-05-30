package gr.gkortsaridis.messenger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

import gr.gkortsaridis.messenger.chat.ChatHistory;
import gr.gkortsaridis.messenger.chat.ChatMessage;
import gr.gkortsaridis.messenger.chat.ChatPersonHistory;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ChatHistory chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView)findViewById(R.id.list);

        SharedPreferences settings = getSharedPreferences("chat", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        chat = new ChatHistory();
        chat = chat.loadChatHistory(this);
        if(chat == null){
            chat = getHistory();
        }

        ChatMenuAdapter chatMenuAdapter = new ChatMenuAdapter(this, chat);
        list.setAdapter(chatMenuAdapter);

        final ChatHistory finalChat = chat;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
                intent.putExtra("personId", finalChat.getChatPersonHistories().get(position).getPersonId());
                startActivity(intent);
            }
        });
    }

    public ChatHistory getHistory(){
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setMyName("George Kortsaridis");

        ArrayList<ChatPersonHistory> chatPersonHistoryArrayList =  new ArrayList<ChatPersonHistory>();

        //Creating ChatPersonHistory with Glidewell
        ChatPersonHistory chatPersonHistory = new ChatPersonHistory("0000","Glidewell");
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        //Message1
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender("Glidewell");
        chatMessage.setDate("26 July 9:03");
        chatMessage.setMe(false);
        chatMessage.setMessage("Would you like to receive our weekly digest?");
        chatMessages.add(chatMessage);
        //Seeting the ChatPersonHistory and adding to History Arraylist
        chatPersonHistory.setMessages(chatMessages);
        chatPersonHistoryArrayList.add(chatPersonHistory);


        //Creating ChatPersonHistory with Person1
        ChatPersonHistory chatPersonHistory1 = new ChatPersonHistory("0001","Kortsaridis Panayotis");
        ArrayList<ChatMessage> chatMessages1 = new ArrayList<>();
        //Message1
        ChatMessage chatMessage1 = new ChatMessage();
        chatMessage1.setSender("Kortsaridis Panayotis");
        chatMessage1.setDate("24 July 23:45");
        chatMessage1.setMe(false);
        chatMessage1.setMessage("Τι κάνεις γιέ μου?");
        chatMessages1.add(chatMessage1);
        //Message2
        chatMessage1 = new ChatMessage();
        chatMessage1.setSender(chatHistory.getMyName());
        chatMessage1.setDate("24 July 23:48");
        chatMessage1.setMe(true);
        chatMessage1.setMessage("Όλα καλά πατέρα! Περνάω κομπλέ στο αμέρικα!");
        chatMessages1.add(chatMessage1);
        //Seeting the ChatPersonHistory and adding to History Arraylist
        chatPersonHistory1.setMessages(chatMessages1);
        chatPersonHistoryArrayList.add(chatPersonHistory1);

        //Creating ChatPersonHistory with Person1
        ChatPersonHistory chatPersonHistory2 = new ChatPersonHistory("0002","Vasilis Manos");
        ArrayList<ChatMessage> chatMessages2 = new ArrayList<>();
        //Message1
        ChatMessage chatMessage2 = new ChatMessage();
        chatMessage2.setSender("Vasilis Manos");
        chatMessage2.setDate("18 June 03:23");
        chatMessage2.setMe(false);
        chatMessage2.setMessage("Κολλητέ γαμάς κανένα μουνάκι ρε?");
        chatMessages2.add(chatMessage2);
        //Seeting the ChatPersonHistory and adding to History Arraylist
        chatPersonHistory2.setMessages(chatMessages2);
        chatPersonHistoryArrayList.add(chatPersonHistory2);

        //Setting the ChatHistory and Saving the data
        chatHistory.setChatPersonHistories(chatPersonHistoryArrayList);
        chatHistory.saveChatHistory(this);
        return chatHistory;
    }

    public void newMessage(View view){
        Log.i("NEW","MESSAGE");
    }
}
