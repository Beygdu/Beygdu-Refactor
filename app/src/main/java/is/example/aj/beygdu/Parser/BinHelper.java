package is.example.aj.beygdu.Parser;

import is.example.aj.beygdu.Utils.DataPrep;

/**
 * Created by arnar on 2/10/2016.
 */
public class BinHelper {

    private String[] elements = new String[] {"h2", "h3", "h4", "th", "tr"} ;

    public BinHelper() {

    }

    public WordResult createResult(String arg) {

        BParser bParser = new BParser(arg, elements);
        bParser.createDocument();
        DataPrep dp = new DataPrep(bParser);

        return dp.getWordResult();
    }
}
