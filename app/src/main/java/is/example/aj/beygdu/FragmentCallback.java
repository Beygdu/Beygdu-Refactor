package is.example.aj.beygdu;

/**
 * @author Arnar
 * @since 01.16
 * @version 1.0
 *
 * An interface that handles all callbacks from fragments to the root activity
 */
public interface FragmentCallback {
    void onSearchCallback(String input, boolean extended);
    void onAboutCallback();
    void onCacheCallback(Object item);
    void onFragmentSwitch(int state);
    void onDebugCallback(String debug);

}
