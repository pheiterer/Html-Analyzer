import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class Connection {
    /**
     * Pegando conteudo HTML da url
     *
     * @throws IOException
     * @param url
     * @return HTML em forma de string
     */
    static String getContent(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        InputStream responseStream = connection.getInputStream();

        Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8);

        scanner.useDelimiter("\\A");

        String content = scanner.next();
        responseStream.close();
        return content;
    }
}
