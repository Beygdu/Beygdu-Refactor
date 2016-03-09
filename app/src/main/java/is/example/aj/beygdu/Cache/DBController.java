package is.example.aj.beygdu.Cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import is.example.aj.beygdu.Parser.Block;
import is.example.aj.beygdu.Parser.SubBlock;
import is.example.aj.beygdu.Parser.Table;
import is.example.aj.beygdu.Parser.WordResult;

/**
 * @author Jón Friðrik
 * @since 14.02.15
 * @version 1.0
 *
 * Modified by Arnar Jonsson 2.2016
 *
 * DBController is the controller for the apps database.
 * It controlls the database by implementing the fetch and
 * insert commands for the database.
 *
 */
public class DBController {

    private Context context = null;
    private SQLiteDatabase dB = null;
    private DBHelper dbHelper = null;

    /**
     * @param context the context the DBController is being used in.
     */
    public DBController(Context context) {
        this.context = context;
    }

    private DBController open() throws SQLException {
        dbHelper = DBHelper.getInstance(context);
        dB = dbHelper.getWritableDatabase();
        return this;
    }

    private void close(Cursor cursor) {
        dbHelper.close();
        if (cursor != null) {
            cursor.close();


        }
    }


    /**
     * Inserts the wordresult into the db.
     *
     * @param wordResult the wordresult
     */
    public void insert(WordResult wordResult) {
        if(!dbContains(wordResult.getTitle())) {
            int rows = getTableSize(DBHelper.TABLE_WORDRESULT);
            if(rows >=  DBHelper.MAX_SIZE)  {
                removeOldest();
            }
            insertWordResult(wordResult);
        }
    }

    private void insertWordResult(WordResult result) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues wordResultContent = new ContentValues();

        wordResultContent.put(DBHelper.TYPE, result.getSearchWord());
        wordResultContent.put(DBHelper.TYPE, result.getDescription());
        wordResultContent.put(DBHelper.TITLE, result.getTitle());
        wordResultContent.put(DBHelper.NOTE, result.getWarning());
        wordResultContent.put(DBHelper.DATE, new Date().getTime());
        dB.insert(DBHelper.TABLE_WORDRESULT, null, wordResultContent);

        int wordResultID = fetchMaxId(DBHelper.WORDID, DBHelper.TABLE_WORDRESULT);
        dbHelper.close();

        for(Block block : result.getResult()) {
            insertBlocks(block, wordResultID);
        }
        close(null);
    }

    private void insertBlocks(Block block, int wordResultId) {
        dB = dbHelper.getWritableDatabase();

        ContentValues blockContent = new ContentValues();
        blockContent.put(DBHelper.WORDID, wordResultId);
        blockContent.put(DBHelper.TITLE, block.getTitle());
        dB.insert(DBHelper.TABLE_BLOCK, null, blockContent);

        int blockid = fetchMaxId(DBHelper.BLOCKID, DBHelper.TABLE_BLOCK);
        dbHelper.close();
        for(SubBlock sb : block.getSubBlocks()) {
            insertSubBlock(sb, blockid);
        }
    }

    private void insertSubBlock(SubBlock sb, int blockID) {
        dB = dbHelper.getWritableDatabase();

        ContentValues subBlockContent = new ContentValues();
        subBlockContent.put(DBHelper.BLOCKID, blockID);
        subBlockContent.put(DBHelper.TITLE, sb.getTitle());
        dB.insert(DBHelper.TABLE_SUBBLOCK, null, subBlockContent);

        int subBlockID = fetchMaxId(DBHelper.SUBBLOCKID, DBHelper.TABLE_SUBBLOCK);
        dbHelper.close();

        for(Table table: sb.getTables()){
            insertTable(table, subBlockID);
        }
    }

    private void insertTable(Table table, int subBlockID) {
        dB = dbHelper.getWritableDatabase();

        ContentValues tableContent = new ContentValues();
        tableContent.put(DBHelper.SUBBLOCKID, subBlockID);
        tableContent.put(DBHelper.TITLE, table.getTitle());
        tableContent.put(DBHelper.COLHEADERS, arrToString(table.getColumnNames()));
        tableContent.put(DBHelper.ROWHEADERS, arrToString(table.getRowNames()));
        tableContent.put(DBHelper.CONTENT, arrToString(table.getContent().toArray()));
        tableContent.put(DBHelper.LAYOUTID, table.getLayoutId());
        dB.insert(DBHelper.TABLE_TABLES, null, tableContent);

        dbHelper.close();
    }

    /**
     *
     * @param title the title to be fetched
     * @return the first occurance for the title in the table
     */
    public WordResult fetch(String title) {
        WordResult newWordResult;

        if (!dB.isOpen()) {
            try {
                open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        final String myQuery =
                "SELECT * FROM wordresult " +
                        "JOIN block ON wordresult.wordid = block.wordid " +
                        "JOIN subblock ON block.blockid = subblock.blockid " +
                        "JOIN tables ON subblock.subblockid = tables.subblockid " +
                        "WHERE wordresult.title = '"+ title +"'";

        Cursor cursor = dB.rawQuery(myQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        newWordResult = new WordResult();
        newWordResult.setDescription(cursor.getString(1));
        newWordResult.setTitle(cursor.getString(2));
        newWordResult.setWarning(cursor.getString(3));
        //newWordResult = new WordResult(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        newWordResult.setResult(fetchBlocks(cursor));


        updateDate(title, cursor);
        close(cursor);
        return newWordResult;
    }

    /**
     *
     * @return all the titles of the words in the database.
     */
    public ArrayList<String> fetchAllWords() {
        ArrayList<String> words= new ArrayList<>();
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String myQuery = "SELECT * FROM " +
                DBHelper.TABLE_WORDRESULT +
                " ORDER BY " + DBHelper.DATE + " DESC";

        Cursor cursor = dB.rawQuery(myQuery, null);

        int iTitle = cursor.getColumnIndex(DBHelper.TITLE);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String tmp = cursor.getString(iTitle);
            words.add(tmp);
        }

        close(cursor);
        return words;
    }



    /**
     *
     * @param title of the word
     * @return title of the word is in the table, else null
     */
    public String fetchObeygjanlegt(String title) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String result = null;

        final String myQuery =
                "SELECT " + DBHelper.WORDID + " FROM " + DBHelper.TABLE_OBEYGJANLEG + " WHERE " +  DBHelper.WORDID + " = '" + title + "'";

        Cursor cursor = dB.rawQuery(myQuery, null);
        cursor.moveToFirst();

        if(cursor.getCount() != 0) {
            result = cursor.getString(0);
        }
        close(cursor);
        return result;
    }

    private ArrayList<Block> fetchBlocks(Cursor cursor) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        int blockID = -1;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if (blockID != cursor.getInt(6)) {
                blockID = cursor.getInt(6);
                blocks.add(new Block(cursor.getString(7), fetchSubBlocks(cursor, cursor.getInt(6))));
            }
        }
        return blocks;
    }

    private ArrayList<SubBlock> fetchSubBlocks(Cursor cursor, int blockID) {
        ArrayList<SubBlock> subBlocks = new ArrayList<SubBlock>();
        int subBlockID = -1;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if(cursor.getInt(9) != subBlockID && cursor.getInt(8) == blockID){
                subBlockID = cursor.getInt(9);
                subBlocks.add(new SubBlock(cursor.getString(10), fetchTables(cursor, cursor.getInt(9))));
            }
        }
        return subBlocks;
    }

    private ArrayList<Table> fetchTables(Cursor cursor, int subBlockID) {
        ArrayList<Table> tables = new ArrayList<Table>();
        int tableID = -1;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if(cursor.getInt(12) != tableID && cursor.getInt(11) == subBlockID) {
                tableID = cursor.getInt(12);
                // TODO :
                tables.add(new Table(cursor.getString(13), stringToArr(cursor.getString(14)),
                        stringToArr(cursor.getString(15)), new ArrayList<String>(Arrays.asList(stringToArr(cursor.getString(16)))), cursor.getInt(17)));
            }
        }
        return tables;
    }

    /**
     *
     * @param column the column to sort
     * @param table the table to sort from
     * @return the highest integer value in the column
     */
    private int fetchMaxId(String column, String table) {
        int id = 0;
        final String MY_QUERY = "SELECT MAX("+ column +") FROM " + table;
        Cursor mCursor = dB.rawQuery(MY_QUERY, null);
        try {
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                id = mCursor.getInt(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    /**
     *
     * @param column the column to sort
     * @param table the table to sort from
     * @return the lowest integer value in the column.
     */
    private int fetchMinId(String column, String table) {
        int id = 0;
        final String MY_QUERY = "SELECT MIN("+ column +") FROM " + table;
        Cursor mCursor = dB.rawQuery(MY_QUERY, null);
        try {
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                id = mCursor.getInt(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        mCursor.close();
        return id;
    }

    /**
     *
     * @param wordTitle the title of the WordResult
     * @return true if db contains word, else false
     */
    private boolean dbContains(String wordTitle){
        boolean contains = false;

        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String myQuery =
                "SELECT * FROM wordresult " +
                        "WHERE " + DBHelper.TITLE + " = '"+ wordTitle +"'";

        Cursor cursor = dB.rawQuery(myQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();

            if(cursor.getCount() > 0){
                contains = true;
                updateDate(wordTitle, cursor);

                close(cursor);
                return contains;
            }
        }

        close(cursor);
        return contains;
    }

    /**
     * updates the date of the word.
     *
     * @param wordTitle the title of the word
     * @param cursor the cursor containing the row to alter
     */
    private void updateDate(String wordTitle, Cursor cursor) {
        String type = cursor.getString(1);
        String title = cursor.getString(2);
        String note = cursor.getString(3);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.TYPE, type);
        contentValues.put(DBHelper.TITLE, title);
        contentValues.put(DBHelper.NOTE, note);
        contentValues.put(DBHelper.DATE, new Date().getTime());

        dB.update(DBHelper.TABLE_WORDRESULT, contentValues, DBHelper.TITLE + "='" + wordTitle + "'", null);
    }

    /**
     *
     * @param table the name of the table
     * @return the size of the table
     */
    private int getTableSize(String table) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String myQuery =
                "SELECT COUNT(*) FROM " + table;

        Cursor cursor = dB.rawQuery(myQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        int value = cursor.getInt(0);
        close(cursor);
        return value;
    }




    /**
     * Removes the oldest word in the table
     */
    private void removeOldest(){
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int wordID = fetchMinId(DBHelper.WORDID, DBHelper.TABLE_WORDRESULT);
        dB.delete(DBHelper.TABLE_WORDRESULT, DBHelper.WORDID + "=" + wordID, null);
        close(null);
    }

    /**
     *
     * @param arr the array of words
     * @return a string in the form word1&word2$word3
     */
    private String arrToString(Object[] arr){
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            result = result + "&" + arr[i];
        }
        return result;
    }

    /**
     *
     * @param s a string in the form word1&word2$word3
     * @return an array containing the words
     */
    private String[] stringToArr(String s) {
        if (s.startsWith("&")) {
            s = s.substring(1, s.length());
        }
        String[] arr = s.split("&+");
        return arr;
    }

}