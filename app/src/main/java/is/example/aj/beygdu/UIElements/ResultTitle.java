package is.example.aj.beygdu.UIElements;

/**
 * Created by arnar on 2/29/2016.
 */
public class ResultTitle implements ResultObject {

    public static final int item_Type = 0;

    private String title;
    private int layoutId;

    private ResultTitle() {

    }

    public static ResultTitle create(String title, int layoutId) {
        ResultTitle rTitle = new ResultTitle();
        rTitle.setTitle(title);
        rTitle.setLayoutId(layoutId);
        return rTitle;
    }

    @Override
    public int getType() {
        return item_Type;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }
}
