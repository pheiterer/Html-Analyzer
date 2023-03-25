import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validation {

    /**
     * Verifica se uma string HTML está estruturada da seguinte forma:<br/>
     *
     *     1. O código HTML está dividido em linhas;<br/>
     *
     *     2. Cada linha pode ser apenas de um dos seguintes tipos:<br/>
     *             a.Tag de abertura (exemplo: < div >)<br/>
     *             b.Tag de fechamento (exemplo: </ div >)<br/>
     *             c.Trecho de texto (exemplo: “Este é o corpo.”)<br/>
     *
     *     3. Uma mesma linha não pode conter dois tipos de conteúdo;<br/>
     *
     *     4. Apenas elementos HTML com pares de tags de abertura e<br/>
     *     fechamento são utilizados (exemplo: < div > e </ div >, mas<br/>
     *     não < br //>)<br/>
     *
     *     5. Tags de abertura não possuem atributos (contra-exemplo: < a href=”link.html” >).<br/>
     *
     * @param html
     * @return true or false
     */
    public static boolean isHTMLStructuredAccordingToPremises(String html) {
        String regex = "(<[a-z]*>\\n)" +    //tags abertura sem atributo
                "|(</\\w*>\\n)" +           //tags de fechamento
                "|([^<\\s].*[^>]\\n)" +     //texto
                "|(</html>)";               //ultima tag
        Matcher matcher = Pattern.compile(regex).matcher(html);
        if (!hasMatchingTags(matcher)) return false;
        return hasUnwantedParts(html, regex);
    }
    private static boolean hasMatchingTags(Matcher matcher) {
        Stack<String> tagStack = new Stack<>();

        while (matcher.find()) {
            String match = matcher.group();
            Matcher matcherLine = Pattern.compile("<(/?\\w+)(\\s+[^>]*)?(/?)>").matcher(match);
            while (matcherLine.find()) {
                String tag = matcherLine.group(1);

                if (tag.startsWith("/")) {
                    if (tagStack.empty() || !tagStack.pop().equals(tag.substring(1))) {
                        return false;
                    }
                } else {
                    tagStack.push(tag);
                }
            }
        }
        return true;
    }

    private static boolean hasUnwantedParts(String html, String regex){
        String[] parts = html.split(regex);
        List<String> unmatchedParts = new ArrayList<>();

        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                unmatchedParts.add(part);
            }
        }
        return unmatchedParts.isEmpty();
    }



}