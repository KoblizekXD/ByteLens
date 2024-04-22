package lol.koblizek.bytelens.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class Resource<T> {

    private final URL url;

    Resource(URL url, Class<T> type) {
        this.url = url;
    }

    public InputStream openStream() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getUrl() {
        return url;
    }

    public String readString() {
        try (BufferedInputStream is = new BufferedInputStream(openStream())) {
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties asProperties() {
        Properties properties = new Properties();
        try (var stream = openStream()) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}
