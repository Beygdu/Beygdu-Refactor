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
public class MailFragment extends Fragment {

    public static final int FRAGMENT_ID = 3;

    private FragmentCallback fragmentCallback;

    public MailFragment() {
        // Required empty public constructor
    }

    public static MailFragment newInstance() {
        MailFragment fragment = new MailFragment();
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
        return inflater.inflate(R.layout.fragment_mail, container, false);
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
