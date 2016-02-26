package is.example.aj.beygdu.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import is.example.aj.beygdu.FragmentCallback;
import is.example.aj.beygdu.R;
import is.example.aj.beygdu.Utils.DisplayUtilities;

// TODO: implement
public class AuthorFragment extends Fragment {

    //Fonts
    private Typeface LatoBold;
    private Typeface LatoSemiBold;
    private Typeface LatoLight;

    //Screen width
    private float width;
    private float height;

    private FragmentCallback fragmentCallback;

    public AuthorFragment() {
        // Required empty public constructor
    }


    public static AuthorFragment newInstance() {
        AuthorFragment fragment = new AuthorFragment();
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
        View v = inflater.inflate(R.layout.fragment_author, container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Context context = getContext();

        width = DisplayUtilities.convertPixelsToDp(
                DisplayUtilities.getScreenWidth(display), context);
        height = DisplayUtilities.convertPixelsToDp(
                DisplayUtilities.getScreenHeigth(display), context);

        LatoBold = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
        LatoSemiBold = Typeface.createFromAsset(context.getAssets(), "Lato-Semibold.ttf");
        LatoLight = Typeface.createFromAsset(context.getAssets(), "Lato-Light.ttf");

        // Select titles
        TextView arnarTitle = (TextView) v.findViewById(R.id.arnarTitle);
        arnarTitle.setTypeface(LatoSemiBold);
        arnarTitle.setTextSize(18);
        TextView danielTitle = (TextView) v.findViewById(R.id.danielTitle);
        danielTitle.setTypeface(LatoSemiBold);
        danielTitle.setTextSize(18);
        TextView jonTitle = (TextView) v.findViewById(R.id.jonTitle);
        jonTitle.setTypeface(LatoSemiBold);
        jonTitle.setTextSize(18);
        TextView snaerTitle = (TextView) v.findViewById(R.id.snaerTitle);
        snaerTitle.setTypeface(LatoSemiBold);
        snaerTitle.setTextSize(18);

        return v;
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
