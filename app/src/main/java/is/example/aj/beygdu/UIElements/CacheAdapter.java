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
public class CacheAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;

    public CacheAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CacheContainer container = null;

        if(convertView == null) {
            container = new CacheContainer();

            convertView = inflater.inflate(R.layout.cache_item, null);
            container.textView = (TextView) convertView.findViewById(R.id.cache_item_textview);
            container.imageView = (ImageView) convertView.findViewById(R.id.cache_item_imageview);
            convertView.setTag(container);
        }
        else {
            container = (CacheContainer) convertView.getTag();
        }

        String str = this.getItem(position);

        container.textView.setText(str);
        container.imageView.setImageResource(R.drawable.ic_menu_camera);
        return convertView;
    }

    static class CacheContainer {
        TextView textView;
        ImageView imageView;
    }

}
