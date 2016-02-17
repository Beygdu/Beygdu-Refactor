package is.example.aj.beygdu.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import is.example.aj.beygdu.FragmentCallback;
import is.example.aj.beygdu.Parser.Block;
import is.example.aj.beygdu.Parser.WordResult;
import is.example.aj.beygdu.R;
import is.example.aj.beygdu.RootActivity;
import is.example.aj.beygdu.UIElements.TableFragment;
import is.example.aj.beygdu.Utils.DisplayUtilities;

public class ResultFragment extends Fragment {

    private FragmentCallback fragmentCallback;

    private TableLayout tableLayout;
    private ArrayList<TableFragment> tables = new ArrayList<TableFragment>();
    private ArrayList<String> blockNames = new ArrayList<String>();
    private ArrayList<Integer> selectedItems = new ArrayList<Integer>();

    private Typeface LatoBold;
    private Typeface LatoSemiBold;
    private Typeface LatoLight;

    private float width;
    private float height;

    private View v;
    private RootActivity activity;
    private WordResult wordResult = new WordResult();

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
        // TODO : implement
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        tableLayout = (TableLayout) v.findViewById(R.id.data_table);

        try {
            wordResult =  getArguments().getParcelable("WordResult");
        } catch (Exception e) {
            e.printStackTrace();
            return v;
        }

        activity = (RootActivity) getActivity();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        width = DisplayUtilities.convertPixelsToDp(size.x, activity);
        height = DisplayUtilities.convertPixelsToDp(size.y, activity);
        /*
        LatoBold = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Bold.ttf");
        LatoSemiBold = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Semibold.ttf");
        LatoLight = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Light.ttf");
        */
        for (int i = 0; i < wordResult.getResult().size(); i++) {
            selectedItems.add(i);
            blockNames.add(wordResult.getResult().get(i).getTitle());
        }
        v = initTables(v);

        // TODO : implement cache memory

        // TODO : manage funnel? filter?

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
/*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
*/
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallback = null;
    }

    private View initTables(View v) {

        TextView titleDesc = (TextView) v.findViewById(R.id.search_result);
        String[] titleArr = wordResult.getTitle().split(" ", 2);
        String firstWord = titleArr[0];
        String rest = titleArr[1];
        titleDesc.setText(Html.fromHtml("<b><big>" + firstWord + "</big></b>" + "\n" + "<small>" + rest + "</small>"));

        if (320 > width && width < 384) {
            titleDesc.setTextSize(35);
        } else if (384 > width && width < 600) {
            titleDesc.setTextSize(35);
        } else if (width > 600) {
            titleDesc.setTextSize(35);
        }
        //titleDesc.setTypeface(LatoLight);

        if (!wordResult.getWarning().equals("")) {
            // TODO : implement
        }

        tables.clear();
        for (int i = 0; i < wordResult.getResult().size(); i++){
            if (selectedItems.contains(i)) {
                Block block = wordResult.getResult().get(i);
                TextView blockTitle = new TextView(activity);
                if (320 > width && width < 384) {
                    blockTitle.setTextSize(20);
                }
                else if(384 > width && width < 600) {
                    blockTitle.setTextSize(28);
                }
                else if(width > 600){
                    blockTitle.setTextSize(42);
                }
                blockTitle.setMinHeight(100);
                blockTitle.setText(block.getTitle());
                blockTitle.setTypeface(LatoLight);
                blockTitle.setTextColor(getResources().getColor(R.color.white));
                blockTitle.setPadding(0, 10, 0, 10);

                TableFragment tFragment = new TableFragment(getContext(), tableLayout, block, blockTitle, firstWord, block.getTitle());
                getFragmentManager().beginTransaction().add(tableLayout.getId(), tFragment).commit();
                tables.add(tFragment);
            }
        }
        return  v;
    }


    @Override
    public void onSaveInstanceState(Bundle instanceState) {

        super.onSaveInstanceState(instanceState);
    }
}
