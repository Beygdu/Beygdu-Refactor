package is.example.aj.beygdu.Parser;

import is.example.aj.beygdu.Utils.DataPrep;

/**
 * @author Arnar Jonsson
 * @version 0.2
 * @since 13.3.2015
 *
 * Refactored 1.2016
 *
 * A helper class from BParser.class.
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
