package video.player.mp4player.videoplayer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import video.player.mp4player.videoplayer.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DrawerItemCustomAdapter  extends BaseAdapter {
    Activity activity;
    int [] imageId;
    private static LayoutInflater inflater=null;
    ArrayList<String> titles;
    public DrawerItemCustomAdapter(Activity activity, ArrayList<String> titles, int[] icons) {

        this.titles = titles;
        this.activity = activity;
        imageId = icons;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return titles.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public class Holder
    {
        TextView tv_title;
        ImageView img_ic_menu;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder=new Holder();
        View view;
        view = inflater.inflate(R.layout.list_view_item_row, null);

        holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        holder.img_ic_menu = (ImageView) view.findViewById(R.id.img_ic_menu);

        holder.tv_title.setText(titles.get(position));

        Glide.with(activity).load(imageId[position]).into(holder.img_ic_menu);

        return view;
    }

}