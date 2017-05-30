package gr.gkortsaridis.messenger;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import gr.gkortsaridis.messenger.chat.ChatHistory;
import gr.gkortsaridis.messenger.chat.ChatMessage;
import gr.gkortsaridis.messenger.chat.ChatPersonHistory;

public class MessagesActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private TextView nameToolbar;
    private Toolbar toolbar;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;

    private int secondsForReply = 2;
    private String name;
    private int icon;
    private IconsLetterColor iconsLetterColor;

    private ChatPersonHistory chatPersonHistory = null;
    ChatHistory chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        iconsLetterColor = new IconsLetterColor();

        String personId = getIntent().getStringExtra("personId");

        chat = new ChatHistory();
        chat = chat.loadChatHistory(this);

        for(int i=0; i<chat.getChatPersonHistories().size(); i++){
            if(chat.getChatPersonHistories().get(i).getPersonId().equals(personId)){
                chatPersonHistory = chat.getChatPersonHistories().get(i);
            }
        }
        name = chatPersonHistory.getPersonName();


        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        nameToolbar = (TextView) findViewById(R.id.nameToolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(iconsLetterColor.getColorFromLetter(name.substring(0,1).toUpperCase()));

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(iconsLetterColor.getColorDarkFromLetter(name.substring(0,1).toUpperCase()));


        nameToolbar.setText(chatPersonHistory.getPersonName());

        loadDummyHistory();

        messageET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    String messageText = messageET.getText().toString();
                    if (TextUtils.isEmpty(messageText)) {
                        return false;
                    }

                    ChatMessage chatMessage = createNewChatMessage(messageText,true);
                    displayMessage(chatMessage);
                    if(messageText.toLowerCase().contains("yes") || messageText.toLowerCase().contains("where")){
                        getReply(messageText);
                    }

                    handled = true;
                }
                return handled;
            }
        });
    }

    public void getReply(final String input){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                final String[] messageText = new String[1];

                if(input.toLowerCase().contains("yes")) {
                      messageText[0] = getResources().getString(R.string.digest_reply);
                    ChatMessage chatMessage = createNewChatMessage(messageText[0],false);
                    displayMessage(chatMessage);
                }else if(input.toLowerCase().contains("where")){
                    messageText[0] = "Order #1 (6000191) is 70% done, expected to arrive Friday July 29, 2016\n\nOrder2# (6000195) is 50% done, expected to arrive Monday August 1, 2016\n\nOrder #3 (6000199) is awaiting your approval. Please access Glidewell App for more information.";
                    ChatMessage chatMessage = createNewChatMessage(messageText[0],false);
                    displayMessage(chatMessage);
                }else{
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setResponseTimeout(1000*100);
                    client.setConnectTimeout(1000*100);
                    client.addHeader("Accept","application/json");
                    try {
                        StringEntity postBody = new StringEntity("{'message':'"+input+"'}");

                        client.post(getApplicationContext(),"https://rocketchattest.qa.crownworlddental.com/api/sendMessage",postBody,"application/json", new AsyncHttpResponseHandler() {

                            @Override
                            public void onStart() {
                                // called before request is started
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                // called when response HTTP status is "200 OK"
                                String str1 = new String(response);


                                try {
                                    JSONObject resp = new JSONObject(str1);
                                    JSONArray messages = resp.getJSONArray("messages");
                                    for(int i=0; i<messages.length(); i++){
                                        String msg = messages.getJSONObject(i).getString("msg");
                                        messageText[0] = msg;
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    messageText[0] = str1;
                                }


                                ChatMessage chatMessage = createNewChatMessage(messageText[0],false);
                                displayMessage(chatMessage);
                                Log.i("Resp",messageText[0]);

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                //messageText[0] = "There was a failure while processing your request :(";
                                messageText[0] = statusCode+" "+(new String(errorResponse));

                                ChatMessage chatMessage = createNewChatMessage(messageText[0],false);
                                displayMessage(chatMessage);

                            }

                            @Override
                            public void onRetry(int retryNo) {
                                // called when request is retried
                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                }


            }
        }, secondsForReply*1000);
    }

    public void sendMessage(View view){
        String messageText = messageET.getText().toString();
        if (TextUtils.isEmpty(messageText)) {
            return;
        }

        //Creating the message
        ChatMessage chatMessage = createNewChatMessage(messageText,true);

        //Displaying it on the screen
        displayMessage(chatMessage);

        //Getting the automatic reply
        getReply(messageText);
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){

        chatHistory = chatPersonHistory.getMessages();  //new ArrayList<ChatMessage>();

        adapter = new ChatAdapter(MessagesActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    public ChatMessage createNewChatMessage(String messageText, boolean me){
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setMessage(messageText);
        chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage.setMe(me);
        if(chatMessage.getIsme()){
            chatMessage.setSender(chat.getMyName());
        }else {
            chatMessage.setSender(name);
        }

        messageET.setText("");

        chat.addNewChatMessage(chatMessage,chatPersonHistory);
        chat.saveChatHistory(this);


        return chatMessage;
    }
}
