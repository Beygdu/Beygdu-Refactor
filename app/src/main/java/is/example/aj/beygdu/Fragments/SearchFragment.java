package is.example.aj.beygdu.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import is.example.aj.beygdu.R;

/**
 * SearchFragment - ? starting screen fragment
 *
 */
public class SearchFragment extends Fragment {

    // Layout objects
    private EditText editText;
    private ImageButton imageButton;
    private CheckBox checkBox;
    private TextView textView;

    // Fragment callback
    private SearchFragmentListener interactionListener;

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);


        // Instantiate layout objects
        editText = (EditText) v.findViewById(R.id.search_input);
        imageButton = (ImageButton) v.findViewById(R.id.search_imagebutton);
        checkBox = (CheckBox) v.findViewById(R.id.search_checkbox);
        textView = (TextView) v.findViewById(R.id.search_checkbox_description);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });

        return v;
    }

    /* Not Needed here
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_about_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    */

    // TODO: Rename method, update argument and hook method into UI event
    public void onSearch() {
        if (interactionListener != null) {
            //
            interactionListener.onSearchFragmentInteraction(editText.getText()+"", checkBox.isChecked());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener) {
            interactionListener = (SearchFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
        interactionListener = null;
    }


    public interface SearchFragmentListener {
        // TODO: Update argument type and name
        void onSearchFragmentInteraction(String s, boolean state);
    }

}
