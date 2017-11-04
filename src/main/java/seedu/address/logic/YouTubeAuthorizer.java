package seedu.address.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;

//@@author jhchia7-reused

/**
 * Return an authorized API client service, such as a YouTube
 * Data API client service to the caller
 */

public class YouTubeAuthorizer {


    /** Application name. */
    private final String APPLICATION_NAME = "API Sample";

    /** Directory to store user credentials for this application. */
    private final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/youtube-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);

    public YouTubeAuthorizer() {
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
    private Credential authorize(Class classToAuthorize) throws IOException, ClassNotFoundException {
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
    private YouTube getYouTubeService(Class classToAuthorize) throws IOException, ClassNotFoundException {
        Credential credential = authorize(classToAuthorize);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Channel getYouTubeChannel(String targetChannelId) {


        YouTube youtube = null;
        try {
            youtube = getYouTubeService(YouTubeAuthorizer.class);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }


        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("part", "statistics,snippet");
        parameters.put("id", targetChannelId);

        YouTube.Channels.List channelsListByIdRequest = null;
        try {
            assert youtube != null;
            channelsListByIdRequest = youtube.channels().list(parameters.get("part").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (parameters.containsKey("id") && parameters.get("id") != "") {
            channelsListByIdRequest.setId(parameters.get("id").toString());
        }

        ChannelListResponse response = null;
        try {
            assert channelsListByIdRequest != null;
            response = channelsListByIdRequest.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Channel youtubeChannel = null;
        try {
            assert response != null;
            youtubeChannel = response.getItems().get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        return youtubeChannel;

    }

}
