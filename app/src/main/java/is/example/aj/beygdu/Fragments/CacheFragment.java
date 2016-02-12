package is.example.aj.beygdu.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import is.example.aj.beygdu.FragmentCallback;
import is.example.aj.beygdu.R;

/**
 */
public class CacheFragment extends Fragment {

    // Layout objects
    private ListView listView;

    // Fragment callback
    private FragmentCallback fragmentCallback;

    public CacheFragment() {
        // Required empty public constructor
    }


    public static CacheFragment newInstance() {
        CacheFragment fragment = new CacheFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cache, container, false);

        listView = (ListView) v.findViewById(R.id.cache_listview);
        listView.setEmptyView(v.findViewById(R.id.cache_empty));

        String[] arr = getArguments().getStringArray("arguments");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.cache_listview_item,
                arr
        );

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentCallback.onCacheCallback(null);
            }

        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onItemClick(Object o) {
        if (fragmentCallback != null) {
            fragmentCallback.onCacheCallback(o);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallback) {
            fragmentCallback = (FragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentCallback");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallback = null;
    }


}