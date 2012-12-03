
package com.betthief.util;

public enum UAString {
    FIREFOX(
            "Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1"),
    CHROME(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1309.0 Safari/537.17");

    public static final String UA_HEADER = "User-Agent";

    private final String value;

    UAString(final String s) {
        value = s;
    }

    @Override
    public String toString() {
        return value;
    }

}
