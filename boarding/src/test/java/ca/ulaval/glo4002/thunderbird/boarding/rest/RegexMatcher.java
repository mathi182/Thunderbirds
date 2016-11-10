package ca.ulaval.glo4002.thunderbird.boarding.rest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class RegexMatcher extends BaseMatcher<java.lang.Object> {
    private final String regex;

    public RegexMatcher(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean matches(Object o) {
        return ((String) o).matches(regex);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("matches regex=");
    }

    public static RegexMatcher matches(String regex) {
        return new RegexMatcher(regex);
    }
}
