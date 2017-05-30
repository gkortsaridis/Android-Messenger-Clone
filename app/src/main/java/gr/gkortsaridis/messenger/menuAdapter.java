package gr.gkortsaridis.messenger;

/**
 * Created by yoko on 23/07/16.
 */import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class menuAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> names;
    private ArrayList<String> msgs;
    private ArrayList<String> whens;

    private static LayoutInflater inflater=null;

    private IconsLetterColor iconsLetterColor;

    public menuAdapter(Activity a, ArrayList<String> names, ArrayList<String> msgs, ArrayList<String> whens) {
        this.activity = a;
        this.names = names;
        this.msgs = msgs;
        this.whens = whens;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iconsLetterColor = new IconsLetterColor();
    }

    public int getCount() {
        return names.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.chatmenu_item, null);

        TextView name = (TextView)vi.findViewById(R.id.name); // title
        TextView msg = (TextView)vi.findViewById(R.id.message); // artist name
        TextView when = (TextView)vi.findViewById(R.id.when); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.image); // thumb image
        TextView iconLetter = (TextView) vi.findViewById(R.id.iconLetter);


        // Setting all values in listview
        name.setText(names.get(position));
        msg.setText(msgs.get(position));
        when.setText(whens.get(position));

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

