package is.example.aj.beygdu.Parser;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arnar on 2/9/2016.
 */
public class Block implements Serializable {

    private String title;
    private ArrayList<SubBlock> subBlocks;

    public Block(String title, ArrayList<SubBlock> content) {
        this.title = title;
        this.subBlocks = content;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<SubBlock> getSubBlocks() {
        return subBlocks;
    }

    public int getBlockSize() {
        return subBlocks.size();
    }

    public boolean hasTitle() {
        return !title.equals("");
    }
}
