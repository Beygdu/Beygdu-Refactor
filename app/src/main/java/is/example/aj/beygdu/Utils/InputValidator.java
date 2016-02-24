package is.example.aj.beygdu.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by arnar on 2/9/2016.
 */
public class InputValidator {


    public static boolean validate(String input, boolean extended) {

        if (!input.contains(" ")) {
            return true;
        }

        String[] legalPrefixes = { "að ",
                "ég ", "þú ", "hann ", "hún ", "það ",
                "við ", "þið ", "þeir ", "þær ", "þau ",
        };

        if( (input.length() - input.replace(" ", "").length() > 1) ) {
            return false;
        }
        else {
            for(String str : legalPrefixes) {
                if(input.contains(str)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String createUrl(String input, boolean extended) {
        String prefix = "http://dev.phpBin.ja.is/ajax_leit.php/?q=";
        String postfix = "&ordmyndir=on";

        if(extended) return prefix + convertToUTF8(input) + postfix;
        return prefix + input;
    }

    public static String createUrl(int id) {
        return "http://dev.phpBin.ja.is/ajax_leit.php/?id=" + id;
    }

    public static String convertToUTF8(String convert) {
        try {
            return URLEncoder.encode(convert, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return convert;
        }
    }
}
