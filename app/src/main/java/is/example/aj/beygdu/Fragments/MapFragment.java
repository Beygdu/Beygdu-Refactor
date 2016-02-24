package is.example.aj.beygdu.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import is.example.aj.beygdu.FragmentCallback;
import is.example.aj.beygdu.R;

// TODO : implement
public class MapFragment extends Fragment {

    private FragmentCallback fragmentCallback;

    private static final double longitude = -21.931015;
    private static final double latitude = 64.146077;

    //MapView mapView;
    //private GoogleMap googleMap;

    public MapFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            // (Probably) Nothing needs to be saved/re-instantiated here
            // Is here as a rule of thumb
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
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
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallback = null;
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {

        // (Probably) Nothing needs to be saved before orientation switch

        super.onSaveInstanceState(instanceState);
    }

}
