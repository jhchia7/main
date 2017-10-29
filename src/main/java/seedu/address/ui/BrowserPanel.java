package seedu.address.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.YouTubeAuthorize;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Channel channel;

    @FXML
    private TextFlow channelTitle;
    @FXML
    private ImageView channelThumbnail;
    @FXML
    private TextFlow channelDescription;
    @FXML
    private TextFlow subscriberCount;
    @FXML
    private TextFlow viewCount;
    @FXML
    private TextFlow createDate;


    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        registerAsAnEventHandler(this);
    }

    /**
     * Calls helper methods to get Channel title, description, subscriber count, view count
     * and create date
     * @param person target person to be selected
     * @throws IOException
     */

    private void loadPersonPage(ReadOnlyPerson person) throws IOException {

        channel = getChannel(person);

        Text title = new Text(getChannelTitle());
        title.setFont(Font.font("Calibri", 40));
        title.setFill(Color.WHITE);
        channelTitle.getChildren().clear();
        channelTitle.getChildren().add(title);

        Text description = new Text("Description:\n" + getChannelDescription());
        description.setFont(Font.font("Calibri", 15));
        description.setFill(Color.WHITE);
        channelDescription.getChildren().clear();
        channelDescription.getChildren().add(description);

        Text subNumber = new Text(getSubCount());
        subNumber.setFont(Font.font("Calibri", 25));
        subNumber.setFill(Color.WHITE);
        subscriberCount.getChildren().clear();
        subscriberCount.getChildren().add(subNumber);

        Text viewNumber = new Text(getViewCount());
        viewNumber.setFont(Font.font("Calibri", 25));
        viewNumber.setFill(Color.WHITE);
        viewCount.getChildren().clear();
        viewCount.getChildren().add(viewNumber);

        Text date = new Text(getCreateDate());
        date.setFont(Font.font("Calibri", 25));
        date.setFill(Color.WHITE);
        createDate.getChildren().clear();
        createDate.getChildren().add(date);

        Image thumbnail = getChannelThumbnail();
        channelThumbnail.setImage(thumbnail);


    }


    /**
     * Frees resources allocated to the browser.
     */

    public void freeResources() {

        channelTitle = null;
        channelThumbnail = null;
        channelDescription = null;
        subscriberCount = null;
        viewCount = null;
        createDate = null;

    }

    private Channel getChannel(ReadOnlyPerson person) {
        String targetChannelId = person.getChannelId().toString();

        YouTube youtube = null;
        try {
            youtube = YouTubeAuthorize.getYouTubeService(BrowserPanel.class);
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

        Channel youtubeChannel = response.getItems().get(0);
        return youtubeChannel;

    }

    private String getChannelTitle() throws IOException {

        return channel.getSnippet().getTitle();

    }

    private Image getChannelThumbnail() {

        Image thumbnail = new Image(channel.getSnippet().getThumbnails().getHigh().getUrl());

        //Image thumbnail = new Image("/images/clock.png");

        return thumbnail;

    }

    private String getChannelDescription() {

        return channel.getSnippet().getDescription();
    }

    private String getSubCount() {

        return "Subscribers: " + channel.getStatistics().getSubscriberCount().toString();
    }

    private String getViewCount() {

        return "Views: " + channel.getStatistics().getViewCount().toString();
    }

    private String getCreateDate() {

        return "Created: " + channel.getSnippet().getPublishedAt().toString();
    }


    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
