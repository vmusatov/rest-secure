package com.restsecure.core.http.cookie;

import com.restsecure.core.http.NameAndValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.http.client.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.TimeZone;

@Data
@Builder
@AllArgsConstructor
public class Cookie implements NameAndValue {
    private String name;
    private String value;
    private String domain;
    private int maxAge = -1;
    private String path;
    private boolean secure;
    private boolean httpOnly;
    private SameSite sameSite;
    private Date expires;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Cookie(String cookie) {
        String[] split = cookie.split(";");

        int position = 0;
        for (String item : split) {
            if (position == 0) {
                int equalIndex = item.indexOf("=");
                this.name = item.substring(0, equalIndex);
                this.value = item.substring(equalIndex + 1);
            } else {
                if (item.contains("=")) {
                    int equalIndex = item.indexOf("=");
                    String name = item.substring(0, equalIndex);
                    String value = item.substring(equalIndex + 1);

                    parseCookieValue(name, value);
                } else {
                    parseCookieValue(item, null);
                }
            }
            position++;
        }
    }

    private void parseCookieValue(String name, String value) {
        switch (name.trim().toLowerCase()) {
            case "path": {
                path = value;
                break;
            }
            case "domain": {
                domain = value;
                break;
            }
            case "expires": {
                expires = DateUtils.parseDate(value);
                break;
            }
            case "max-age": {
                maxAge = Integer.parseInt(value);
                break;
            }
            case "httponly": {
                httpOnly = true;
                break;
            }
            case "secure": {
                secure = true;
                break;
            }
            case "samesite": {
                sameSite = SameSite.parseValue(value);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(name);
        if (value != null) {
            builder.append("=").append(value);
        }
        if (path != null) {
            builder.append(";").append("Path").append("=").append(path);
        }
        if (domain != null) {
            builder.append(";").append("Domain").append("=").append(domain);
        }
        if (expires != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            builder.append(";").append("Expires").append("=").append(simpleDateFormat.format(expires));
        }
        if (maxAge != -1) {
            builder.append(";").append("Max-Age").append("=").append(maxAge);
        }
        if (httpOnly) {
            builder.append(";").append("HttpOnly");
        }
        if (secure) {
            builder.append(";").append("Secure");
        }
        if (sameSite != null) {
            builder.append(";").append("SameSite").append("=").append(sameSite);
        }
        return builder.toString();
    }

    public enum SameSite {
        None,
        Strict,
        Lax;

        private static final EnumSet<SameSite> ALL_VALUES = EnumSet.allOf(SameSite.class);

        public static SameSite parseValue(String value) {
            return ALL_VALUES.stream()
                    .filter(sameSite -> sameSite.name().equalsIgnoreCase(value))
                    .findFirst()
                    .orElse(null);
        }
    }
}