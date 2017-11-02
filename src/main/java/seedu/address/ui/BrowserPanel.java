package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.api.services.youtube.model.Channel;
import com.google.common.eventbus.Subscribe;

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

        registerAsAnEventHandler(this);
    }

    /**
     * Calls helper methods to get Channel title, description, subscriber count, view count
     * and create date
     * @param person target person to be selected
     * @throws IOException
     */

    private void loadPersonPage(ReadOnlyPerson person) throws IOException {

        channel = YouTubeAuthorize.getYouTubeChannel(person.getChannelId().toString());

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

        Text subNumber = new Text("Subscribers: " + getSubCount());
        subNumber.setFont(Font.font("Calibri", 25));
        subNumber.setFill(Color.WHITE);
        subscriberCount.getChildren().clear();
        subscriberCount.getChildren().add(subNumber);

        Text viewNumber = new Text("Views: " + getViewCount());
        viewNumber.setFont(Font.font("Calibri", 25));
        viewNumber.setFill(Color.WHITE);
        viewCount.getChildren().clear();
        viewCount.getChildren().add(viewNumber);

        Text date = new Text("Created: " + getCreateDate());
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


    private String getChannelTitle() throws IOException {

        return channel.getSnippet().getTitle();

    }

    private Image getChannelThumbnail() {

        Image thumbnail = new Image(channel.getSnippet().getThumbnails().getHigh().getUrl());

        return thumbnail;

    }

    private String getChannelDescription() {

        return channel.getSnippet().getDescription();
    }

    private String getSubCount() {

        return formatNumber(channel.getStatistics().getSubscriberCount().longValue());
    }

    private String getViewCount() {

        return formatNumber(channel.getStatistics().getViewCount().longValue());
    }

    private String getCreateDate() {
        return channel.getSnippet().getPublishedAt().toStringRfc3339();

    }

    /**
     * Formats number with thousand, million and billion suffix
     * @param number to be formatted with suffix
     * @return
     */
    private String formatNumber(long number) {
        final long thousand = 1000L;
        final long million = 1000000L;
        final long billion = 1000000000L;

        if (number >= billion) {
            return String.format("%.1f%c", (double) number / billion, 'b');
        } else if (number >= million) {
            return String.format("%.1f%c", (double) number / million, 'm');
        } else if (number >= thousand) {
            return String.format("%.1f%c", (double) number / thousand, 'k');
        } else {
            return number + "";
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
