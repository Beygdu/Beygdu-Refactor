package is.example.aj.beygdu.Utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arnar
 * @since 02.16
 * @version 0.1
 *
 * A helper class that prepares possible word corrections from the SKRAMBI post request
 */
public class OutputValidator {

    private static String[] forceEncoding(String[] args) {
        String[] decodingArray = new String[args.length];
        for(int i = 0; i < args.length; i++) {
            try {
                Properties p = new Properties();
                p.load(new StringReader("key="+args[i]));
                decodingArray[i] = p.getProperty("key");
            }
            catch ( Exception e ) {
                e.printStackTrace();
                decodingArray[i] = args[i];
            }
        }
        return decodingArray;
    }

    private static boolean isCorrect(String str) {
        return !str.contains("[]");
    }

    public static String[] createSkrambiOutput(String str) {
        if(!isCorrect(str)) {
            return null;
        }

        // Remove brackets
        str = str.substring(1, str.length()-1);

        // Remove suggestions
        str = str.substring(str.indexOf("[")+1, str.indexOf("]"));

        ArrayList<String> temp = new ArrayList<String>();

        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) {
            temp.add(matcher.group(1));
        }
        String[] rawResults = new String[temp.size()];
        int count = 0;
        for(String s : temp) {
            rawResults[count++] = s;
        }

        return forceEncoding(rawResults);
    }

}
