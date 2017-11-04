# jhchia7-reused
###### \java\seedu\address\logic\YouTubeAuthorize.java
``` java

/**
 * Return an authorized API client service, such as a YouTube
 * Data API client service to the caller
 */

public final class YouTubeAuthorize {


    /** Application name. */
    private static final String APPLICATION_NAME = "API Sample";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/youtube-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create an authorized Credential object.
     * @param classToAuthorize Class to return the API client service to
     * @return an authorized Credential object.
     * @throws IOException
     */
    private static Credential authorize(Class classToAuthorize) throws IOException, ClassNotFoundException {
        //Class myClass = Class.forName(className);
        // Load client secrets.
        InputStream in =
                classToAuthorize.getClass().getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized API client service, such as a YouTube
     * Data API client service.
     * @param classToAuthorize Class to return the API client service to
     * @return an authorized API client service
     * @throws IOException
     */
    public static YouTube getYouTubeService(Class classToAuthorize) throws IOException, ClassNotFoundException {
        Credential credential = authorize(classToAuthorize);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static Channel getYouTubeChannel(String targetChannelId) {

        YouTube youtube = null;
        try {
            youtube = YouTubeAuthorize.getYouTubeService(YouTubeAuthorize.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("part", "statistics,snippet");
        parameters.put("id", targetChannelId);

        YouTube.Channels.List channelsListByIdRequest = null;
        try {
            channelsListByIdRequest = youtube.channels().list(parameters.get("part").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (parameters.containsKey("id") && parameters.get("id") != "") {
            channelsListByIdRequest.setId(parameters.get("id").toString());
        }

        ChannelListResponse response = null;
        try {
            response = channelsListByIdRequest.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Channel youtubeChannel = null;
        try {
            youtubeChannel = response.getItems().get(0);
        } catch (IndexOutOfBoundsException e) {
            return youtubeChannel;
        }

        return youtubeChannel;

    }

}
```