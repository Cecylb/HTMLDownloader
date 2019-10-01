package downloader;

import java.util.regex.Pattern;

public enum ParsablePatterns {

    PNG("PNG", Pattern.compile("https:\\/\\/.[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]?.png")),
    //HTTP("HTTP", Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")),
    JPG("JPG", Pattern.compile("https:\\/\\/.[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]?.jpg")),
    //SVG("SVG", Pattern.compile("https.+?.svg")),
    CSS("CSS", Pattern.compile("<link.*?href=\".+?\\.css.*?\".+?>")),
    JS("JS", Pattern.compile("<script.+?src=\".+?\\.js.*?></script>")),
    HTTP("HTTP", Pattern.compile("^(http[s]?://www\\.|http[s]?://|www\\.)")),

    A("<A", Pattern.compile("<a")),
    BASE("<BASE", Pattern.compile("<base")),
    IMG("<IMG", Pattern.compile("<img")),
    FRAME("<FRAME", Pattern.compile("<frame")),
    BRACKET(">", Pattern.compile(">"));

    public String patternType;
    public Pattern pattern;

    ParsablePatterns(final String patternType, final Pattern pattern) {
        this.patternType = patternType;
        this.pattern = pattern;
    }
}
