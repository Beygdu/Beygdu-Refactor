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


public class AuthorFragment extends Fragment {

    private FragmentCallback callback;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_author, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallback) {
            callback = (FragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }


}
