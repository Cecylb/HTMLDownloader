package downloader;

import java.util.regex.Pattern;

public enum ParsablePatterns {

    PNG(Pattern.compile("https:\\/\\/.[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]?.png")),
    JPG(Pattern.compile("https:\\/\\/.[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]?.jpg")),
    CSS(Pattern.compile("<link.*?href=\".+?\\.css.*?\".+?>")),
    JS(Pattern.compile("<script.+?src=\".+?\\.js.*?></script>")),
    HTTP(Pattern.compile("^(http[s]?://www\\.|http[s]?://|www\\.)"));

    public Pattern pattern;

    ParsablePatterns(final Pattern pattern) {
        this.pattern = pattern;
    }
}
