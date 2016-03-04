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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import is.example.aj.beygdu.FragmentCallback;
import is.example.aj.beygdu.Parser.Block;
import is.example.aj.beygdu.Parser.SubBlock;
import is.example.aj.beygdu.Parser.Table;
import is.example.aj.beygdu.Parser.WordResult;
import is.example.aj.beygdu.R;
import is.example.aj.beygdu.RootActivity;
import is.example.aj.beygdu.UIElements.ResultAdapter;
import is.example.aj.beygdu.UIElements.ResultItemAdapter;
import is.example.aj.beygdu.UIElements.ResultObject;
import is.example.aj.beygdu.UIElements.ResultTable;
import is.example.aj.beygdu.UIElements.ResultTitle;
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
        // TODO : implement a better solution
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    /*
        // Screen W/H
        width = DisplayUtilities.getScreenWidth(
                getActivity().getWindowManager().getDefaultDisplay());
        height = DisplayUtilities.getScreenHeigth(
                getActivity().getWindowManager().getDefaultDisplay());

        // Typefaces sdsd
        LatoBold = Typeface.createFromAsset(activity.getAssets(), "Lato-Bold.ttf");
        LatoSemiBold = Typeface.createFromAsset(activity.getAssets(), "Lato-Semibold.ttf");
        LatoLight = Typeface.createFromAsset(activity.getAssets(), "Lato-Light.ttf");
    */
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        //tableLayout = (TableLayout) v.findViewById(R.id.data_table);

        try {
            wordResult =  getArguments().getParcelable("WordResult");
        } catch (Exception e) {
            e.printStackTrace();
            return v;
        }

        ArrayList<ResultObject> objects = createObjectArray();

        ListView listView = (ListView) v.findViewById(R.id.result_listview);
        ResultItemAdapter itemAdapter = new ResultItemAdapter(getContext());
        itemAdapter.addItems(objects);
        listView.setAdapter(itemAdapter);

        return v;
    /*
        // Orientation change //
        if(savedInstanceState != null) {
            blockNames = savedInstanceState.getStringArrayList("blockNames");
            selectedItems = savedInstanceState.getIntegerArrayList("selectedItems");
            width = savedInstanceState.getFloat("width");
            height = savedInstanceState.getFloat("height");
            wordResult = savedInstanceState.getParcelable("WordResult");
        }
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

        LatoBold = Typeface.createFromAsset(activity.getAssets(), "Lato-Bold.ttf");
        LatoSemiBold = Typeface.createFromAsset(activity.getAssets(), "Lato-Semibold.ttf");
        LatoLight = Typeface.createFromAsset(activity.getAssets(), "Lato-Light.ttf");

        for (int i = 0; i < wordResult.getResult().size(); i++) {
            selectedItems.add(i);
            blockNames.add(wordResult.getResult().get(i).getTitle());
        }
        v = initTables(v);

        // TODO : implement cache memory

        // TODO : manage funnel? filter?

        return v;
        */
    }

    private ArrayList<ResultObject> createObjectArray() {
        if(wordResult == null) {
            // TODO errorhandling
            ResultTitle errorObject = ResultTitle.create("This is an error", -1);
            ArrayList<ResultObject> list = new ArrayList<>();
            list.add(errorObject);
            return list;
        }
        else {
            ArrayList<ResultObject> list = new ArrayList<>();

            list.add(ResultTitle.create(wordResult.getTitle(), 0));

            // TODO : implement the warning

            for(Block block : wordResult.getResult()) {

                if(block.getTitle() == null || !block.getTitle().equals("")) {
                    list.add(ResultTitle.create(block.getTitle(), 1));
                }

                for(SubBlock subBlock : block.getSubBlocks()) {

                    if(subBlock.getTitle() == null || !subBlock.getTitle().equals("")) {
                        list.add(ResultTitle.create(subBlock.getTitle(), 2));
                    }

                    for(Table table : subBlock.getTables()) {

                        list.add(ResultTable.create(
                                table.getTitle(),
                                table.getColumnNames(),
                                table.getRowNames(),
                                table.getContent(),
                                table.getLayoutId()
                        ));

                    }

                }

            }

            return list;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_result_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.fragment_result_filter:
                fragmentCallback.onDebugCallback("MenuItem Selected");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentCallback = null;
    }

    private View initTables(View v) {
/*
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
        titleDesc.setTypeface(LatoLight);

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


                TableFragment tFragment = TableFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Block", block);
                bundle.putString("wordTitle", firstWord);
                bundle.putString("blockTitle", block.getTitle());
                tFragment.setArguments(bundle);


                //TableFragment tFragment = new TableFragment(getContext(), tableLayout, block, blockTitle, firstWord, block.getTitle());
                getFragmentManager().beginTransaction().add(tableLayout.getId(), tFragment).commit();
                tables.add(tFragment);
            }
        }
        return  v;*/
        return null;
    }

/*
    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        instanceState.putStringArrayList("blockNames", blockNames);
        instanceState.putIntegerArrayList("selectedItems", selectedItems);
        instanceState.putFloat("width", width);
        instanceState.putFloat("height", height);
        instanceState.putParcelable("WordResult", wordResult);
        super.onSaveInstanceState(instanceState);
    }*/

    /////
    //
    /////
    /*
    private RelativeLayout.LayoutParams createTextViewLP(int controlId) {

        switch (controlId) {
            // Page Header
            case 0:
                RelativeLayout.LayoutParams header =
                        new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                100);
                int hM = DisplayUtilities.integerToDp(getContext(), 20);
                header.setMargins(hM, hM, hM, hM);
                return header;
            // Word Warning
            case 1:
                // TODO : implement
                return null;
            // Block Title
            case 2:
                return null;
            // SubBlock Title
            case 3:
                return null;
            // Table title
            case 4:
                return null;
            default:
                return null;
        }

    }

    private TextView manageTitleLayoutParams(TextView textView, int controlId) {

        switch (controlId) {
            // Header TextView
            case 0:
                textView.setTextSize(35);
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getResources().getColor(R.color.header_title));
                return textView;
            // Warning TextView
            case 1:
                // TODO : implement
                return null;
            // Block TextView
            case 2:
                if (320 > width && width < 384) {
                    textView.setTextSize(22);
                }
                else if(384 > width && width < 600) {
                    textView.setTextSize(28);
                }
                else if(width > 600){
                    textView.setTextSize(42);
                }
                textView.setMinHeight(
                        DisplayUtilities.integerToDp(
                                getContext(), 80));
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getResources().getColor(R.color.block_title));
                int bP = DisplayUtilities.integerToDp(getContext(), 10);
                textView.setPadding(0, bP, 0, bP);
                return textView;
            // SubBlock TextView
            case 3:
                if (320 > width && width < 384) {
                    textView.setTextSize(20);
                }
                else {
                    textView.setTextSize(22);
                }
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getResources().getColor(R.color.subblock_title));
                int bSP = DisplayUtilities.integerToDp(getContext(), 10);
                textView.setPadding(0, bSP, 0, bSP);
                return textView;
            // Table TestView
            case 4:
                if (320 > width && width < 384) {
                    textView.setTextSize(18);
                }
                else {
                    textView.setTextSize(20);
                }
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getResources().getColor(R.color.table_title));
                int tP = DisplayUtilities.integerToDp(getContext(), 10);
                textView.setPadding(0, tP, 0, tP);
                textView.setBackgroundResource(
                        R.drawable.top_border_orange);
                return textView;
            // Cell TextView
            case 5:
                textView.setGravity(Gravity.LEFT);
                textView.setTextSize(16);
                textView.setTypeface(LatoLight);
                textView.setTextColor(
                        getResources().getColor(R.color.table_cell));
                int tabP = DisplayUtilities.integerToDp(getContext(), 10);
                textView.setPadding(tabP/2, tabP, tabP, tabP);
                // TODO : implement clicklistener
                return textView;
            default:
                return textView;
        }
    }

    private TextView setCellBackroundResource(TextView textView) {
        // TODO : implement
        return textView;
    }*/
}
