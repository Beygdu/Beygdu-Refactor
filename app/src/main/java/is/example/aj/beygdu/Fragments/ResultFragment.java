package is.example.aj.beygdu.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import is.example.aj.beygdu.FragmentCallback;
import is.example.aj.beygdu.Parser.WordResult;
import is.example.aj.beygdu.R;

public class ResultFragment extends Fragment {

    private FragmentCallback fragmentCallback;

    private int[] blocksShown;

    public ResultFragment() {
        // Required empty public constructor
    }


    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
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
        View v = inflater.inflate(R.layout.fragment_result, container, false);

        Bundle bundle = getArguments();
        WordResult result = new WordResult();

        try {
            result = (WordResult) bundle.getSerializable("WordResult");
        }
        catch (Exception e) {
            fragmentCallback.onDebugCallback("try-catch failure in ResultFragment");
        }

        if(result == null) {
            TextView titleTextView = (TextView) v.findViewById(R.id.result_textview);
            titleTextView.setText("Failure occured");
        }
        else {
            // TODO: Fragment title (searchwords)
            //fragmentCallback.onDebugCallback("I am calling you from Result");

            // TODO : uncommend and change?
            //TextView titleTextView = (TextView) v.findViewById(R.id.result_textview);
            //titleTextView.setText(result.getDescription());

            // TODO: Warning (if needed)
            if(result.getWarning() != null && result.getWarning().equals("") ) {
                TextView alertTextview = (TextView) v.findViewById(R.id.result_textview);
                alertTextview.setText(Html.fromHtml(result.getWarning()));
            }

            // TODO: Manage blocks
        }


        return v;
    }

    public void onLongClick() {
        // TODO : implement some kind of long-click listener
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

    private TableRow createTableRow() {
        TableRow tableRow = new TableRow(getActivity().getApplicationContext());
        // TODO : Specific layoutparam instructions go here
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT
        );

        tableRow.setLayoutParams(params);
        return tableRow;
    }



}
