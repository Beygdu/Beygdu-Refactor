package is.example.aj.beygdu;

/**
 * Created by arnar on 2/9/2016.
 */
public interface FragmentCallback {
    void onSearchCallback(String input, boolean extended);
    void onAboutCallback();
    void onCacheCallback(Object item);
    void onFragmentSwitch(int state);
    void onDebugCallback(String debug);

}
