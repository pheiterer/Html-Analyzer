import javax.naming.directory.InvalidAttributesException;
import java.io.IOException;

/**
 * <b>Teste técnico: Software Development Intern</b><p>
 * A partir de uma URL, obtem o trecho de texto contido no
 * nível mais profundo da estrutura HTML de seu conteúdo
 *
 * @author Paulo Henrique dos Santos Eiterer
 */
public class HtmlAnalyzer {

    public static void main(String[] args) {
        String url = args[0];
        try {
            String html = Connection.getContent(url);

            if (!Validation.isHTMLStructuredAccordingToPremises(html)) {
                throw new InvalidAttributesException("malformed HTML");
            }

            System.out.println(DeepestText.find(html));

        } catch (IOException e) {
            System.out.println("URL connection error");

        } catch (InvalidAttributesException e) {
            System.out.println(e.getMessage());
        }
    }
}


