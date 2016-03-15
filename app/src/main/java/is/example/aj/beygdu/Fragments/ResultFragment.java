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
import android.widget.AbsListView;
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

/**
 * @author Jón Friðrik Jónatansson, Daniel Pall
 * @since 25.10.14
 * @version 1.0
 *
 * Refactored by Aranr Jonsson 2.2016
 *
 * A fragment that houses result from the BIN search
 */
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

        View v = inflater.inflate(R.layout.fragment_result, container, false);

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

        //TextView footerView = createFooterView();
        //listView.addFooterView(footerView);

        return v;

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
            if(!wordResult.getWarning().equals("")) {
                list.add(ResultTitle.create(wordResult.getWarning(), 3));
            }

            for(Block block : wordResult.getResult()) {

                if(!block.getTitle().equals("")) {
                    list.add(ResultTitle.create(block.getTitle(), 1));
                }

                for(SubBlock subBlock : block.getSubBlocks()) {

                    if(!subBlock.getTitle().equals("")) {
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

            // Page footer
            //list.add(ResultTitle.create(getContext().getResources().getString(R.string.search_footer_description), 4));

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

}
