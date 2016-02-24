package is.example.aj.beygdu.UIElements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import is.example.aj.beygdu.Parser.WordResult;
import is.example.aj.beygdu.R;

/**
 * Created by arnar on 2/23/2016.
 */
public class CacheAdapter extends ArrayAdapter<WordResult> {

    private LayoutInflater inflater;

    public CacheAdapter(Context context, int resource, List<WordResult> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null) {
            v = inflater.inflate(R.layout.cache_item, parent, false);
        }
        TextView textView = (TextView) v.findViewById(R.id.cache_item_textview);
        ImageView imageView = (ImageView) v.findViewById(R.id.cache_item_imageview);

        WordResult wR = this.getItem(position);

        textView.setText(wR.getSearchWord());
        imageView.setImageResource(R.drawable.ic_menu_camera);

        return v;
    }


}
