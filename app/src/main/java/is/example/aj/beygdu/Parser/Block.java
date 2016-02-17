package is.example.aj.beygdu.Parser;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arnar on 2/9/2016.
 */
public class Block implements Parcelable {

    private String title;
    private ArrayList<SubBlock> subBlocks;

    public Block(String title, ArrayList<SubBlock> content) {
        this.title = title;
        this.subBlocks = content;
    }

    protected Block(Parcel in) {
        title = in.readString();
        subBlocks = in.createTypedArrayList(SubBlock.CREATOR);
    }

    public static final Creator<Block> CREATOR = new Creator<Block>() {
        @Override
        public Block createFromParcel(Parcel in) {
            return new Block(in);
        }

        @Override
        public Block[] newArray(int size) {
            return new Block[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(subBlocks);
    }
}
