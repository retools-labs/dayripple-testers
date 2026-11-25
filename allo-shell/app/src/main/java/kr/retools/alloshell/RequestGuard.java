package kr.retools.alloshell;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

public final class RequestGuard {
    private RequestGuard() {
    }

    public static boolean isBlocked(String method, String url) {
        if (method == null || url == null) {
            return true;
        }
        if (url.equals("file:///android_asset/control.html")) {
            return false;
        }

        final URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            return true;
        }

        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (scheme == null || host == null
                || !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
            return true;
        }

        String normalizedMethod = method.toUpperCase(Locale.ROOT);
        String normalizedHost = host.toLowerCase(Locale.ROOT);
        boolean isAllo = normalizedHost.equals("allthatlocal.com")
                || normalizedHost.endsWith(".allthatlocal.com");
        boolean readMethod = normalizedMethod.equals("GET") || normalizedMethod.equals("HEAD");

        if (!isAllo) {
            return !readMethod;
        }

        if (readMethod || normalizedMethod.equals("OPTIONS")) {
            return false;
        }

        return !(normalizedMethod.equals("POST")
                && "/api/guide/delivery-quote".equals(uri.getPath()));
    }
}
