package is.example.aj.beygdu.Parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import is.example.aj.beygdu.Utils.Bstring;

/**
 * @author Arnar Jonsson
 * @since 24.01.2015
 * @version 1.0
 *
 * Refactored 06.2015
 *
 * BParser - creates a Jsoup document from an url
 */
public class BParser {

    private Bstring url;
    private String[] elements;
    private boolean assertKey;

    private Document doc;
    private ArrayList<Element> selectedElements;

    public BParser(String url, String[] elements) {
        this.url = new Bstring(url);
        this.elements = elements;
    }

    public Document getDocument() {
        return doc;
    }

    public String[] getElements() {
        return elements;
    }

    public String asString() {
        return doc.toString();
    }


    public ArrayList<Element> getSelectedElements() {
        if(elements == null) return null;

        if(selectedElements == null) {

            selectedElements = new ArrayList<Element>();
            Element body = doc.body();

            for( Element element : body.getAllElements() ) {

                if( isPreferedElement( element ) ) {
                    selectedElements.add(element);
                }

            }

        }

        return selectedElements;
    }

    public boolean containsNode(String nodeName) {
        Elements body = doc.body().getAllElements();
        for( Element element : body ) {
            if(element.hasClass(nodeName)) {
                return true;
            }
        }

        return false;
    }

    public boolean Assert() {
        return assertKey;
    }

    private boolean isPreferedElement( Element element ) {
        if( elements == null ) return false;

        for( String tag : elements ) {
            if( element.nodeName().equals( tag )) {
                return true;
            }
        }
        return false;
    }

    public void createDocument() {
        try {
            doc = Jsoup.connect(url.get()).get();
            assertKey = true;
        }
        catch (IOException e) {
            Log.w("BParser createDocument", e.toString());
            assertKey = false;
        }
    }
}
