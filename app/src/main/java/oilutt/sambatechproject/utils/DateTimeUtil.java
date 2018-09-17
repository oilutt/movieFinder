package oilutt.sambatechproject.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateTimeUtil {
    public static final String PATTERN_DATE_DB = "yyyy-MM-dd";
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();


    private DateTimeUtil() {
        super();
    }

    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     * <p/>
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     * The parser will be lenient toward the parsed date.
     *
     * @param str           the date to parse, not null
     * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException           if none of the date patterns were suitable (or there were none)
     */
    public static Date parseDate(String str, String... parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, parsePatterns);
    }

    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     * <p/>
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     *
     * @param str           the date to parse, not null
     * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws IllegalArgumentException if the pattern array is null
     * @throws ParseException           if none of the date patterns were suitable
     */
    private static Date parseDateWithLeniency(String str, String... parsePatterns)
            throws ParseException {
        if (str == null) {
            return null;
        }

        if (parsePatterns == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }

        SimpleDateFormat parser = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        parser.setLenient(true);
        ParsePosition pos = new ParsePosition(0);
        for (String parsePattern : parsePatterns) {

            String pattern = parsePattern;

            // LANG-530 - need to make sure 'ZZ' output doesn't get passed to SimpleDateFormat
            if (parsePattern.endsWith("ZZ")) {
                pattern = pattern.substring(0, pattern.length() - 1);
            }

            parser.applyPattern(pattern);
            pos.setIndex(0);

            String str2 = str;
            // LANG-530 - need to make sure 'ZZ' output doesn't hit SimpleDateFormat as it will ParseException
            if (parsePattern.endsWith("ZZ")) {
                int signIdx = indexOfSignChars(str2, 0);
                while (signIdx >= 0) {
                    str2 = reformatTimezone(str2, signIdx);
                    signIdx = indexOfSignChars(str2, ++signIdx);
                }
            }

            Date date = parser.parse(str2, pos);
            if (date != null && pos.getIndex() == str2.length()) {
                return date;
            }
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }

    /**
     * Gets the actual date.
     * <p/>
     * This method don't validate the date.
     *
     * @return The Actual date, without time, in {@code String}.
     */
    public static String getDateNowString() {
        return getDateTimeNow(PATTERN_DATE_DB, false);
    }

    /**
     * Gets the actual datetime
     *
     * @param format  The date format.
     * @param lenient true if need to validate the date.
     * @return The actual Datetime in {@code String}.
     */
    private static String getDateTimeNow(String format, boolean lenient) {
        Calendar c = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat(format, DEFAULT_LOCALE);
        formatter.setLenient(lenient);

        return formatter.format(c.getTime());
    }

    /**
     * Index of sign characters (i.e. '+' or '-').
     *
     * @param str      The string to search
     * @param startPos The start position
     * @return the index of the first sign character or -1 if not found
     */
    private static int indexOfSignChars(String str, int startPos) {
        int idx = TextUtils.indexOf(str, '+', startPos);
        if (idx < 0) {
            idx = TextUtils.indexOf(str, '-', startPos);
        }
        return idx;
    }

    /**
     * Reformat the timezone in a date string.
     *
     * @param str     The input string
     * @param signIdx The index position of the sign characters
     * @return The reformatted string
     */
    private static String reformatTimezone(String str, int signIdx) {
        String str2 = str;
        if (signIdx >= 0 &&
                signIdx + 5 < str.length() &&
                Character.isDigit(str.charAt(signIdx + 1)) &&
                Character.isDigit(str.charAt(signIdx + 2)) &&
                str.charAt(signIdx + 3) == ':' &&
                Character.isDigit(str.charAt(signIdx + 4)) &&
                Character.isDigit(str.charAt(signIdx + 5))) {
            str2 = str.substring(0, signIdx + 3) + str.substring(signIdx + 4);
        }
        return str2;
    }

    /**
     * Gets the actual date in the desired hour/minute/seconds/milliseconds.
     *
     * @param hour         the desired hour.
     * @param minute       the desired minute.
     * @param seconds      the desired seconds.
     * @param milliseconds the desired milliseconds.
     * @return An actual Date object in the desired time.
     */
    @Deprecated
    public static Date getDateNow(int hour, int minute, int seconds, int milliseconds) {
        Calendar cal = setTimeCalendar(Calendar.getInstance(), hour, minute, seconds, milliseconds);
        return cal.getTime();
    }

    /**
     * Changes the hour from a Calendar object passed as parameter
     *
     * @param calendar     The calendar that will be changed.
     * @param hour         The hour to update.
     * @param minutes      The minutes to update.
     * @param seconds      The seconds to update.
     * @param milliseconds The milliseconds to update.
     * @return A Calendar Object with the hour updated.
     */
    public static Calendar setTimeCalendar(Calendar calendar, int hour, int minutes, int seconds, int milliseconds) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);

        return calendar;
    }

    /**
     * Convert an Date object to Calendar.
     */
    public static Calendar toCalendar(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String getAbreviatedMonth(Calendar calendar) {
        switch (calendar.get(Calendar.MONTH)) {
            case 0:
                return "Jan";
            case 1:
                return "Fev";
            case 2:
                return "Mar";
            case 3:
                return "Abr";
            case 4:
                return "Mai";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Ago";
            case 8:
                return "Set";
            case 9:
                return "Out";
            case 10:
                return "Nov";
            case 11:
                return "Dez";
            default:
                return "";
        }
    }

    public static String getDay(Date date) {
        Calendar calendar = DateTimeUtil.toCalendar(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String getYear(Date date) {
        Calendar calendar = DateTimeUtil.toCalendar(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
}
