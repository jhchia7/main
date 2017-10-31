package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangePwdCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an ChangePwdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeThemeCommand parse(String args) throws ParseException {
        try {
            String[] array = ParserUtil.parseByDelimiter(args, " ");
            if (array.length == 2) {
                if (array[1].equals("light")) {
                    return new ChangeThemeCommand("LightTheme");
                } else if (array[1].equals("dark")) {
                    return new ChangeThemeCommand("DarkTheme");
                }
            }
            throw new ParseException(
                    String.format(ChangeThemeCommand.MESSAGE_NO_THEME, ChangeThemeCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }
    }

}
