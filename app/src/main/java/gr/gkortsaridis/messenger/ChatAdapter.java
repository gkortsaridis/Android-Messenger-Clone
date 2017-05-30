package gr.gkortsaridis.messenger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import gr.gkortsaridis.messenger.chat.ChatMessage;

/**
 * Created by yoko on 23/07/16.
 */

public class ChatAdapter extends BaseAdapter {

    private final List<ChatMessage> chatMessages;
    private Activity context;
    private IconsLetterColor iconsLetterColor;

    public ChatAdapter(Activity context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
        this.iconsLetterColor = new IconsLetterColor();
    }

    @Override
    public int getCount() {
        if (chatMessages != null) {
            return chatMessages.size();
        } else {
            return 0;
        }
    }

    @Override
    public ChatMessage getItem(int position) {
        if (chatMessages != null) {
            return chatMessages.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ChatMessage chatMessage = getItem(position);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = vi.inflate(R.layout.list_item_chat_message, null);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        boolean myMsg = chatMessage.getIsme();
        setAlignment(holder, myMsg);
        holder.txtMessage.setText(chatMessage.getMessage());
        holder.txtInfo.setText(chatMessage.getDate());

        holder.iconFrom.setImageBitmap(getCircleBitmap(chatMessage.getSender().substring(0, 1).toUpperCase()));
        holder.iconTo.setImageBitmap(getCircleBitmap(chatMessage.getSender().substring(0, 1).toUpperCase()));
        holder.letterFrom.setText(chatMessage.getSender().substring(0,1).toUpperCase());
        holder.letterTo.setText(chatMessage.getSender().substring(0,1).toUpperCase());

        return convertView;
    }

    public void add(ChatMessage message) {
        chatMessages.add(message);
    }

    public void add(List<ChatMessage> messages) {
        chatMessages.addAll(messages);
    }

    private void setAlignment(ViewHolder holder, boolean isMe) {
        if (!isMe) {

            holder.iconTo.setVisibility(View.GONE);
            holder.letterTo.setVisibility(View.GONE);
            holder.iconFrom.setVisibility(View.VISIBLE);
            holder.letterFrom.setVisibility(View.VISIBLE);

            //Setting the background image of the LinearLayout
            holder.contentWithBG.setBackgroundResource(R.drawable.bubble);

            //Setting the Layout Parameters of the LinearLayout
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtInfo.setLayoutParams(layoutParams);
            holder.txtMessage.setTextColor(context.getResources().getColor(R.color.white) );
            holder.txtInfo.setTextColor(context.getResources().getColor(R.color.white) );
        } else {

            holder.iconTo.setVisibility(View.VISIBLE);
            holder.letterTo.setVisibility(View.VISIBLE);
            holder.iconFrom.setVisibility(View.GONE);
            holder.letterFrom.setVisibility(View.GONE);

            holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtMessage.setLayoutParams(layoutParams);
            holder.txtMessage.setTextColor(context.getResources().getColor(R.color.black) );
            holder.txtInfo.setTextColor(context.getResources().getColor(R.color.black) );

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtInfo.setLayoutParams(layoutParams);
        }
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
        holder.txtInfo = (TextView) v.findViewById(R.id.txtInfo);
        holder.iconFrom = (ImageView) v.findViewById(R.id.iconFrom);
        holder.iconTo = (ImageView) v.findViewById(R.id.iconTo);
        holder.letterFrom = (TextView) v.findViewById(R.id.letterFrom);
        holder.letterTo = (TextView) v.findViewById(R.id.letterTo);

        return holder;
    }

    private static class ViewHolder {
        public TextView txtMessage;
        public TextView txtInfo;
        public LinearLayout content;
        public LinearLayout contentWithBG;
        public ImageView iconFrom;
        public ImageView iconTo;
        public TextView letterFrom;
        public TextView letterTo;
    }

    private Bitmap getCircleBitmap(String letter) {
        //Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a);

        final Bitmap output = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = iconsLetterColor.getColorFromLetter(letter);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, 50, 50);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(output, rect, rect, paint);

        //bitmap.recycle();

        return output;
    }
}