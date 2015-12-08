package burp.eventStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Util
{
    public static String escapeJsonString(String input)
    {
        if (input == null)
        {
            return "null";
        }

        return "\"" + input.replaceAll("[\"]", "\\\\$0").replaceAll("\\n", "\\\\n") + "\"";
    }

    public static String isoTime(long time)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return df.format(new Date(time));
    }
}