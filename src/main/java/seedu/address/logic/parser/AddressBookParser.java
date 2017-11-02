package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangePwdCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.logic.commands.FindFavCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SendCommand;
import seedu.address.logic.commands.SortAscendingCommand;
import seedu.address.logic.commands.UndoCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case FavCommand.COMMAND_WORD:
        case FavCommand.COMMAND_ALIAS:
            return new FavCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case FindEmailCommand.COMMAND_WORD:
            return new FindEmailCommandParser().parse(arguments);

        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);

        case FindFavCommand.COMMAND_WORD:
        case FindFavCommand.COMMAND_ALIAS:
            return new FindFavCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case SendCommand.COMMAND_WORD:
        case SendCommand.COMMAND_ALIAS:
            return new SendCommandParser().parse(arguments);

        case SortAscendingCommand.COMMAND_WORD:
            return new SortAscendingCommand();

        case ChangePwdCommand.COMMAND_WORD:
        case ChangePwdCommand.COMMAND_ALIAS:
            return new ChangePwdCommandParser().parse(arguments);

        case ChangeThemeCommand.COMMAND_WORD:
        case ChangeThemeCommand.COMMAND_ALIAS:
            return new ChangeThemeCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Searches for all commands that contains the input text
     * @param text
     * @return ArrayList of command words
     */

    public ArrayList<String> filterCommand(String text) throws IllegalValueException {
        String[] array = ParserUtil.parseByDelimiter(text, " ");
        ArrayList<String> result = new ArrayList<String>();
        if (AddCommand.COMMAND_WORD.contains(array[0])) {
            result.add(AddCommand.COMMAND_HELP);
        }
        if (ChangePwdCommand.COMMAND_WORD.contains(array[0])) {
            result.add(ChangePwdCommand.COMMAND_HELP);
        }
        if (EditCommand.COMMAND_WORD.contains(array[0])) {
            result.add(EditCommand.COMMAND_HELP);
        }
        if (DeleteCommand.COMMAND_WORD.contains(array[0])) {
            result.add(DeleteCommand.COMMAND_HELP);
        }
        if (FavCommand.COMMAND_WORD.contains(array[0])) {
            result.add(FavCommand.COMMAND_HELP);
        }
        if (ClearCommand.COMMAND_WORD.contains(array[0])) {
            result.add(ClearCommand.COMMAND_HELP);
        }
        if (FindCommand.COMMAND_WORD.contains(array[0])) {
            result.add(FindCommand.COMMAND_HELP);
        }
        if (ListCommand.COMMAND_WORD.contains(array[0])) {
            result.add(ListCommand.COMMAND_HELP);
        }
        if (HistoryCommand.COMMAND_WORD.contains(array[0])) {
            result.add(HistoryCommand.COMMAND_HELP);
        }
        if (HelpCommand.COMMAND_WORD.contains(array[0])) {
            result.add(HelpCommand.COMMAND_HELP);
        }
        if (UndoCommand.COMMAND_WORD.contains(array[0])) {
            result.add(UndoCommand.COMMAND_HELP);
        }
        if (ExitCommand.COMMAND_WORD.contains(array[0])) {
            result.add(ExitCommand.COMMAND_HELP);
        }
        if (SendCommand.COMMAND_WORD.contains(array[0])) {
            result.add(SendCommand.COMMAND_HELP);
        }
        if (RedoCommand.COMMAND_WORD.contains(array[0])) {
            result.add(RedoCommand.COMMAND_HELP);
        }
        if (SelectCommand.COMMAND_WORD.contains(array[0])) {
            result.add(SelectCommand.COMMAND_HELP);
        }
        if (ChangeThemeCommand.COMMAND_WORD.contains(array[0])) {
            result.add(ChangeThemeCommand.COMMAND_HELP);
        }
        return result;
    }

}
