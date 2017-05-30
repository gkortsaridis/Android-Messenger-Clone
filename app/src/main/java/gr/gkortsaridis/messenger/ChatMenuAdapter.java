package gr.gkortsaridis.messenger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gr.gkortsaridis.messenger.chat.ChatHistory;
import gr.gkortsaridis.messenger.chat.ChatMessage;
import gr.gkortsaridis.messenger.chat.ChatPersonHistory;

/**
 * Created by yoko on 23/07/16.
 */
public class ChatMenuAdapter extends BaseAdapter {

    private Activity activity;
    private ChatHistory chatHistory;
    private ArrayList<String> names,dates,msgs;


    private static LayoutInflater inflater=null;

    private IconsLetterColor iconsLetterColor;

    public ChatMenuAdapter(Activity activity, ChatHistory chatHistory) {
        this.activity = activity;
        this.chatHistory = chatHistory;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iconsLetterColor = new IconsLetterColor();

        names = new ArrayList<>();
        dates = new ArrayList<>();
        msgs  = new ArrayList<>();

        for(int i=0; i<chatHistory.getChatPersonHistories().size(); i++){
            Log.i("New Person",i+"");
            ChatPersonHistory chatPersonHistory = chatHistory.getChatPersonHistories().get(i);
            ChatMessage lastMessage = chatPersonHistory.getMessages().get(chatPersonHistory.getMessages().size()-1);

            names.add(chatPersonHistory.getPersonName());
            dates.add(lastMessage.getDate());
            if(lastMessage.getIsme()) {
                msgs.add("You: "+lastMessage.getMessage());
            }else{
                msgs.add(lastMessage.getMessage());
            }
        }

    }

    public int getCount() {
        return chatHistory.getChatPersonHistories().size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null) vi = inflater.inflate(R.layout.chatmenu_item, null);

        TextView name = (TextView)vi.findViewById(R.id.name); // title
        TextView msg = (TextView)vi.findViewById(R.id.message); // artist name
        TextView when = (TextView)vi.findViewById(R.id.when); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.image); // thumb image
        TextView iconLetter = (TextView) vi.findViewById(R.id.iconLetter);


        // Setting all values in listview
        name.setText(names.get(position));
        msg.setText(msgs.get(position));
        when.setText(dates.get(position));

        thumb_image.setImageBitmap(getCircleBitmap(names.get(position).substring(0,1).toUpperCase()));
        iconLetter.setText(names.get(position).substring(0,1));

        return vi;
    }

    private Bitmap getCircleBitmap(String letter) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.a);

        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = iconsLetterColor.getColorFromLetter(letter);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(output, rect, rect, paint);

        bitmap.recycle();

        return output;
    }
}