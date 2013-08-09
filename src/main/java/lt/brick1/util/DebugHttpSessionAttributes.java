package lt.brick1.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import javax.servlet.http.HttpSession;

public class DebugHttpSessionAttributes {
    public static String debugInfoOfAttributesOf(HttpSession session) {
        if (session == null) {
            return "    ---------- no HTTP session";
        }
        Enumeration<String> names = session.getAttributeNames();
        String attributes = "";
        int nr = 0;
        int totalSize = 0;
        while (names.hasMoreElements()) {
            nr += 1;
            String name = names.nextElement();
            Object attribute = session.getAttribute(name);
            int size = findSizeViaSerialization(attribute);
            attributes += "\n    " + nr + ". "
                    + "(bytes=" + prependUpTo(7, ' ', withThousandSeparators(",", size)) + ") "
                    + name + " ===> " + attribute;
            if (size > 0) {
                totalSize += size;
            }
        }
        attributes += "\n    --------- " + prependUpTo(7, ' ', withThousandSeparators(",", totalSize));
        return attributes;
    }

    private static int findSizeViaSerialization(Object object) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(object);
            objectStream.close();
            return byteStream.toByteArray().length;
        } catch (IOException ex) {
            return -1;
        }
    }

    public static String withThousandSeparators(String separator, long number) {
        if (number == 0) {
            return "0";
        }
        String sign = number < 0 ? "-" : "";
        String result = "";
        number = Math.abs(number);
        while (number > 0) {
            String digits = Long.toString(number % 1000);
            number /= 1000;
            if (number > 0) {
                digits = prependUpTo(3, '0', digits);
            }
            result = (number > 0 ? separator : "") + digits + result;
        }
        return sign + result;
    }

    public static String prependUpTo(int minWidth, char prependedChar, String text) {
        if (text == null || text.length() >= minWidth) {
            return text;
        }
        return fillChar(prependedChar, minWidth - text.length()) + text;
    }

    public static String fillChar(char c, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(c);
        }
        return result.toString();
    }
}
