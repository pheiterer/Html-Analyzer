import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DeepestText {
    /**
     * Itera sobre cada linha procurando onde está o texto de nivel mais profundo
     *
     * @param html
     * @return O texto do nível mais profundo
     */
    public static String find(String html) {
        int deepestLevel = -1;
        String deepestText = "";
        Stack<String> tagStack = new Stack<>();
        String[] lines = html.split("\n");


        for (String line : lines) {
            Matcher tags = Pattern.compile("<(/?\\w+)[^>]*>").matcher(line);
            Matcher text = Pattern.compile("^\\s*([^<]+)\\s*$").matcher(line);

            while (tags.find()) {
                String tag = tags.group(1);
                if (tag.startsWith("/")) {
                    tagStack.pop();
                } else {
                    tagStack.push(tag);
                }
            }

            while (text.find()) {
                String textHTML = text.group(1);
                if (!textHTML.isEmpty()) {
                    int level = tagStack.size();
                    if (level > deepestLevel) {
                        deepestLevel = level;
                        deepestText = textHTML;
                    }
                }
            }
        }
        return deepestText;
    }
}
