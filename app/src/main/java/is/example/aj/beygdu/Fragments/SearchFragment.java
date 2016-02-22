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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import is.example.aj.beygdu.FragmentCallback;
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
    private FragmentCallback fragmentCallback;

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

        if(savedInstanceState != null) {
            // Nothing needs to be saved/re-instantiated here
            // Is here as a rule of thumb
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        int orientation = getActivity().getResources().getConfiguration().orientation;


        // Instantiate layout objects
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TableLayout tableLayout = (TableLayout) v.findViewById(R.id.tableLayout1);
            tableLayout.setLayoutParams(setTableLayoutParams());

            TextView textView = (TextView) v.findViewById(R.id.title);
            textView.setVisibility(View.GONE);

        }

        editText = (EditText) v.findViewById(R.id.search_edittext);
        imageButton = (ImageButton) v.findViewById(R.id.search_imagebutton);
        checkBox = (CheckBox) v.findViewById(R.id.search_checkbox);
        textView = (TextView) v.findViewById(R.id.search_checkbox_description);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });

        ImageButton mapButton = (ImageButton) v.findViewById(R.id.search_footer_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallback.onFragmentSwitch(5);
            }
        });

        ImageButton contactButton = (ImageButton) v.findViewById(R.id.search_footer_contact);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallback.onFragmentSwitch(4);
            }
        });

        return v;
    }



    public void onSearch() {
        if (fragmentCallback != null) {
            fragmentCallback.onSearchCallback(editText.getText() + "", checkBox.isChecked());
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

        // Nothing needs to be saved before orientation switch

        super.onSaveInstanceState(instanceState);
    }


    private RelativeLayout.LayoutParams setTableLayoutParams() {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(0, 20, 0, 0);

        return params;
    }


}
