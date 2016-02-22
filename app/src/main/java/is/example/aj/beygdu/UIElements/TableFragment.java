package is.example.aj.beygdu.UIElements;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Config;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import is.example.aj.beygdu.Parser.Block;
import is.example.aj.beygdu.Parser.SubBlock;
import is.example.aj.beygdu.Parser.Table;
import is.example.aj.beygdu.R;
import is.example.aj.beygdu.Utils.DisplayUtilities;

/**
 * Created by arnar on 2/14/2016.
 */
public class TableFragment extends Fragment {

    private Context context;
    private TableLayout tableLayout;
    private Block block;
    private Table table;
    private TextView title;

    private Typeface LatoBold;
    private Typeface LatoSemiBold;
    private Typeface LatoLight;

    private int subBlockTitleText = 22;
    private int tableTitleText = 18;
    private int cellText = 16;

    public TableFragment() {
        // Empty Constructor
    }

    // TODO : Fragments with these kinds of constructors are bad
    // TODO : Implement regular bundle argument passing, find fix for layouts
    // TODO : Fix fonts, remember that there is no specified folder for em
    public TableFragment(Context context, TableLayout tableLayout, Block block, TextView title, String wordTitle, String blockTitle) {
        this.context = context;
        this.tableLayout = tableLayout;
        generateViewId(this.tableLayout);
        this.block = block;
        this.title = title;
        generateViewId(this.title);


        /* Fonts
        LatoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");
        LatoSemiBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Semibold.ttf");
        LatoLight = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf");
        */
    }

    public TableFragment(Context context, TableLayout tableLayout, Table table, String wordTitle) {
        this.context = context;
        this.tableLayout = tableLayout;
        generateViewId(this.tableLayout);
        this.table = table;

        /* Fonts
        LatoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");
        LatoSemiBold = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Semibold.ttf");
        LatoLight = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf");
        */
    }

    public String getTitle() {
        return title.getText().toString();
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.item_table, container, false);

        if(savedInstanceState != null) {
            title = (TextView) rootView.findViewById(R.id.search_result);
            tableLayout = (TableLayout) rootView.findViewById(R.id.data_table);
            block = savedInstanceState.getParcelable("block");
            table = savedInstanceState.getParcelable("table");
        }

        createBlock();

        return rootView;
        //Set typeface for fonts

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true);
    }
/*
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getView() != null) {
            ViewGroup parent = (ViewGroup) getView().getParent();
            if(parent != null) {
                parent.removeAllViews();
            }
        }
    }
*/
    public void createBlock() {
        tableLayout.addView(title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //Iterate through sub-blocks and set title
        for (SubBlock sBlock : block.getSubBlocks()) {

            //Special case for nafnháttur
            if (sBlock.getTitle().equals("Nafnháttur")) {
                // Text title
                TextView nafnhatturTitle = new TextView(context);
                nafnhatturTitle.setText(sBlock.getTitle());
                nafnhatturTitle.setTextSize(subBlockTitleText);
                //nafnhatturTitle.setMinHeight(70);
                //nafnhatturTitle.setTypeface(LatoLight);
                nafnhatturTitle.setPadding(0, 20, 20, 20);
                nafnhatturTitle.setTextColor(getResources().getColor(R.color.white));
                tableLayout.addView(nafnhatturTitle);

                TextView tableTitle = new TextView(context);
                tableTitle.setText(sBlock.getTitle());
                createTableSpecial(sBlock.getTables().get(0));
                continue;
            }

            if (sBlock.getTitle().equals("Sagnbót")) {
                System.out.println(sBlock.getTitle());
            }

            //Special case for lýsingarháttur nútíðar
            if (block.getTitle().toLowerCase().equals("lýsingarháttur nútíðar")) {
                TextView tableTitle = new TextView(context);
                tableTitle.setText(sBlock.getTitle());
                tableLayout.addView(tableTitle);
                createTableSpecial(sBlock.getTables().get(0));
                continue;
            }

            // The rest of the tables
            if (!sBlock.getTitle().equals("")) {
                TextView subBlockTitle = new TextView(context);
                subBlockTitle.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                subBlockTitle.setText(sBlock.getTitle());
                subBlockTitle.setTextSize(subBlockTitleText);
                //subBlockTitle.setMinHeight(70);
                //subBlockTitle.setTypeface(LatoLight);
                subBlockTitle.setTextColor(getResources().getColor(R.color.white));
                subBlockTitle.setPadding(0, 80, 20, 20);
                tableLayout.addView(subBlockTitle);
            }
            //Create the tables and set title
            for (final Table tables : sBlock.getTables()) {
                final TextView tableTitle = new TextView(context);
                tableTitle.setText(tables.getTitle());
                tableTitle.setTextSize(tableTitleText);
                tableTitle.setTextSize(20);
                tableTitle.setTypeface(LatoLight);
                tableTitle.setTextColor(getResources().getColor(R.color.white));
                tableTitle.setBackgroundResource(R.drawable.top_border_orange);
                tableTitle.setPadding(16, 10, 0, 10);
                tableTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_copy, 0);

                tableLayout.addView(tableTitle);
                createTable(tables);
            }
        }
    }

    private void createTableSpecial(Table table) {
        TableRow tr = new TableRow(context);
        final TextView cell = new TextView(context);
        cell.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        cell.setPadding(20, 10, 0, 0);
        cell.setTextSize(20);
        cell.setBackgroundResource(R.drawable.top_border_orange);
        cell.setTypeface(LatoLight);
        cell.setTextColor(getResources().getColor(R.color.white));
        cell.setClickable(true);
        //cell.setOnLongClickListener(new XLongClickListener(context, cell));
        cell.setText(table.getContent().get(0));
        cell.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_copy, 0);


        tr.addView(cell);
        tableLayout.addView(tr);
    }

    private void createTable(Table table) {
        final int rowNum = table.getRowNames().length;
        final int colNum = table.getColumnNames().length;
        final int rowLast = rowNum - 1;

        int contentIndex = 0;
        int counter = 0;
        for (int row = 0; row < rowNum; row++) {
            TableRow tr = new TableRow(context);
            //tr.setPadding(20, 10, 0, 10);

            // For small tables like, nafnbót and sagnbót
            if (rowNum < 2) {
                tr.setBackgroundResource(R.drawable.bottom_top_border_blue);
            }

            // Even numbers and not last row
            else if (row % 2 == 0 && (row != rowLast)) {
                tr.setBackgroundResource(R.drawable.top_border_blue);
            }


            // Odd numbers and not last row
            else if ((row % 1 == 0) && (row != rowLast)) {
                tr.setBackgroundResource(R.drawable.top_border_white);
            }

            // Last row
            else if (row == rowLast) {
                if (row % 2 == 0) {
                    tr.setBackgroundResource(R.drawable.bottom_top_border_blue);
                } else {
                    tr.setBackgroundResource(R.drawable.bottom_top_border_white);
                }
            }

            for (int col = 0; col < colNum; col++) {
                final TextView cell = new TextView(context);
                cell.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                cell.setGravity(Gravity.LEFT);
                //cell.setTypeface(LatoLight);
                cell.setTextSize(cellText);
                cell.setPadding(5,10,0,10);
                /*
                if (!(row == 0 || col == 0)) {
                    cell.setClickable(true);
                    cell.setOnLongClickListener(new XLongClickListener(context, cell));
                }
                */
                if (row % 2 == 0) {
                    cell.setTextColor(getResources().getColor(R.color.font_default));
                } else {
                    cell.setTextColor(getResources().getColor(R.color.font_default));
                }


                if (row == 0) {
                    if( col == 0) {
                        cell.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f));
                    }
                    if (table.getContent().size() == 1) {
                        cell.setText(table.getContent().get(row));
                    } else {
                        cell.setText(table.getColumnNames()[col]);
                    }
                } else {
                    if (col == 0) {
                        cell.setText(table.getRowNames()[row]);
                        cell.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f));
                    } else {
                        String cellString = table.getContent().get(contentIndex++);

                        if (cellString.contains("/")) {
                            String firstLine = cellString.split("/")[0];
                            String secondLine = cellString.split("/")[1];
                            cellString = firstLine + "/" + System.getProperty("line.separator") + secondLine.trim();
                        }

                        /**
                         * Special string concatination for layout like
                         * hann
                         * hún  komið
                         * það
                         */
                        int whiteSpaceCount = cellString.length() - cellString.replaceAll(" ", "").length();
                        String lastWord = "";
                        String allButLast = "";
                        if(whiteSpaceCount > 0 && !(cellString.contains("/")) ) {
                            String[] allWords = cellString.split(" ");
                            lastWord = allWords[allWords.length - 1];
                            if(whiteSpaceCount > 2) {
                                allButLast += allWords[0] + "<br/>" + allWords[1] + "   <b>" + lastWord + "</b><br/>" + allWords[2];
                            }
                            else if(whiteSpaceCount > 0) {
                                allButLast += allWords[0] + " <b>" + lastWord + "</b>";
                            }
                        }
                        if(whiteSpaceCount > 0 && !(cellString.contains("/"))) {
                            cell.setText(Html.fromHtml(allButLast));
                        }
                        else {
                            cell.setText(cellString);
                        }

                    }
                }
                tr.addView(cell);
            }
            tableLayout.addView(tr);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {

        instanceState.putParcelable("block", block);
        instanceState.putParcelable("table", table);
        instanceState.putString("titleTextView", title.getText().toString());
        super.onSaveInstanceState(instanceState);
    }


    private void generateViewId(View v) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.setId(DisplayUtilities.generateViewId());
        }
        else {
            //v.setId(View.generateViewId());
        }
    }
}
