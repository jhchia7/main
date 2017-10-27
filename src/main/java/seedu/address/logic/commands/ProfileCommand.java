package seedu.address.logic.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.YouTubeAuthorize;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Selects a person identified using it's last displayed index from the address book and prints information
 * of the person's YouTube channel.
 */

public class ProfileCommand extends Command {

    public static final String COMMAND_WORD = "profile";
    public static final String COMMAND_ALIAS = "p";
    public static final String COMMAND_HELP = "profile INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PROFILE_PERSON_SUCCESS = "Displaying profile of: %1$s";

    private final Index targetIndex;

    public ProfileCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToView = lastShownList.get(targetIndex.getZeroBased());
        String targetChannelId = personToView.getChannelId().toString();


        YouTube youtube = null;
        try {
            youtube = YouTubeAuthorize.getYouTubeService(ProfileCommand.class);
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
        Channel channel = response.getItems().get(0);
        System.out.println(channel.getSnippet().getTitle());
        System.out.println(channel.getSnippet().getDescription());
        System.out.println(channel.getStatistics().getSubscriberCount() + " Subscribers");
        System.out.println(channel.getStatistics().getViewCount() + " Total view count");


        String channelTitle = channel.getSnippet().getTitle();
        String channelDescription = channel.getSnippet().getDescription();
        String channelSubCount = channel.getStatistics().getSubscriberCount() + " Subscribers";
        String channelViewCount = channel.getStatistics().getViewCount() + " Total view count";

        String showUser = channelTitle + "\n" + channelDescription + "\n" + channelSubCount + "\n"
                + channelViewCount + "\n";


        return new CommandResult(String.format(showUser, targetIndex.getOneBased()));

    }

}
