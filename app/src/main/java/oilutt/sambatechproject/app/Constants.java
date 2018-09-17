package oilutt.sambatechproject.app;

public class Constants {

    public static final String URL_BASE = "https://api.themoviedb.org/3/";
    public static final String THE_MOVIEDB_API_KEY = "17741933e61920f90034102728d9d170";
    public static final String LANGUAGE = "pt-br";

    public class SharedPreferences {
        public static final String PREFERENCES_NAME = "sambatechproject_ppreferences";
        public static final String CONFIG = "CONFIG";
        public static final String LIST_FAV_MOVIES = "LIST_FAV_MOVIES";
    }

    public class Retrofit {
        public static final String CONFIGURATION = "configuration";
        public static final String DISCOVER_MOVIES = "discover/movie";
        public static final String MOVIE_DETAILS = "movie/{movie_id}";
    }

    public class Extras {
        public static final String ID_MOVIE = "ID_MOVIE";
        public static final String NAME_MOVIE = "NAME_MOVIE";
        public static final String ORDER_BY = "ORDER_BY";
        public static final String FILTER_BY = "FILTER_BY";
        public static final String PATH_MOVIE = "PATH_MOVIE";
    }

    public class IntentCode {
        public static final int FILTER = 1234;
    }

    public class SortBy {
        public static final String POPULARITY = "popularity.desc";
        public static final String RATE = "vote_average.desc";
        public static final String RELEASE = "release_date.desc";
    }
}
