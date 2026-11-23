package kr.retools.alloshell;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RequestGuardTest {
    @Test
    public void allowsSafeRequestsToAllo() {
        assertFalse(RequestGuard.isBlocked("GET", "https://allthatlocal.com/stores"));
        assertFalse(RequestGuard.isBlocked("HEAD", "https://sub.allthatlocal.com/image.png"));
        assertFalse(RequestGuard.isBlocked("OPTIONS", "https://allthatlocal.com/api/orders"));
    }

    @Test
    public void blocksWritesToAlloExceptDeliveryQuote() {
        assertTrue(RequestGuard.isBlocked("POST", "https://allthatlocal.com/api/orders"));
        assertFalse(RequestGuard.isBlocked("POST",
                "https://allthatlocal.com/api/guide/delivery-quote"));
        assertTrue(RequestGuard.isBlocked("PUT",
                "https://shop.allthatlocal.com/api/guide/delivery-quote"));
    }

    @Test
    public void protectsOtherHostsAndRejectsLookalikes() {
        assertFalse(RequestGuard.isBlocked("GET", "https://maps.google.com/map"));
        assertTrue(RequestGuard.isBlocked("POST", "https://analytics.example.com/event"));
        assertTrue(RequestGuard.isBlocked("POST", "https://evil-allthatlocal.com/order"));
    }

    @Test
    public void handlesCasePortsAndInvalidUrlsSafely() {
        assertTrue(RequestGuard.isBlocked("post", "HTTPS://ALLTHATLOCAL.COM:443/order"));
        assertFalse(RequestGuard.isBlocked("get", "HTTPS://ALLTHATLOCAL.COM:443/"));
        assertTrue(RequestGuard.isBlocked("GET", "not a url"));
    }
}
