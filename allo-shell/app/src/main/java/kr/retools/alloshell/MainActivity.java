package kr.retools.alloshell;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.ServiceWorkerClient;
import android.webkit.ServiceWorkerController;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class MainActivity extends Activity {
    private static final String TAG = "AlloShell";
    private static final String GUARD_TAG = "AlloGuard";
    private static final String BLOCKED_BODY = "Blocked by Allo Shell test guard";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        WebView.setWebContentsDebuggingEnabled(true);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportMultipleWindows(false);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return guard(request.getMethod(), request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return blockUnsupportedScheme(request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return blockUnsupportedScheme(url);
            }

            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
                Log.w(TAG, "renderProcessGone didCrash=" + detail.didCrash()
                        + " priorityAtExit=" + detail.rendererPriorityAtExit());
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                    GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.deny();
            }
        });

        ServiceWorkerController.getInstance().setServiceWorkerClient(new ServiceWorkerClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebResourceRequest request) {
                return guard(request.getMethod(), request.getUrl().toString());
            }
        });

        setContentView(webView);
        webView.loadUrl(BuildConfig.ALLO_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy isFinishing=" + isFinishing()
                + " isChangingConfigurations=" + isChangingConfigurations());
        super.onDestroy();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.i(TAG, "onTrimMemory level=" + level);
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private static WebResourceResponse guard(String method, String url) {
        if (!RequestGuard.isBlocked(method, url)) {
            return null;
        }
        Log.w(GUARD_TAG, method + " " + url);
        return new WebResourceResponse(
                "text/plain",
                "UTF-8",
                403,
                "Forbidden",
                Collections.emptyMap(),
                new ByteArrayInputStream(BLOCKED_BODY.getBytes(StandardCharsets.UTF_8)));
    }

    private static boolean blockUnsupportedScheme(String url) {
        if ("file:///android_asset/control.html".equals(url)) {
            return false;
        }
        if (url.regionMatches(true, 0, "http:", 0, 5)
                || url.regionMatches(true, 0, "https:", 0, 6)) {
            return false;
        }
        Log.w(GUARD_TAG, "scheme blocked " + url);
        return true;
    }
}
