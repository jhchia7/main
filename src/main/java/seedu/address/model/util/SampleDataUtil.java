package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.ChannelId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Felix Arvid Ulf Kjellberg"), new Phone("87438807"),
                           new Email("pewdiepie@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new ChannelId("UC-lHJZR3Gqxm24_Vd_AJ5Yw"),
                    getTagSet("friends"), new Favourite("false")),
                new Person(new Name("Soren Wood"), new Phone("99272758"), new Email("sorenwood@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new ChannelId("UCQD3awTLw9i8Xzh85FKsuJA"),
                    getTagSet("colleagues", "friends"), new Favourite("false")),
                new Person(new Name("Edberg Oliveiro"), new Phone("93210283"), new Email("edberg@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new ChannelId("UCvlvI5s742g4cJiXS_ZYdfQ"),
                    getTagSet("neighbours"), new Favourite("false")),
                new Person(new Name("Aamir"), new Phone("91031282"), new Email("aamir@example.com"),
                            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                            new ChannelId("UCuHQqPko8f0lZtc8D2Z7BoA"),
                    getTagSet("family"), new Favourite("false")),
                new Person(new Name("Lisa Smith"), new Phone("92492021"), new Email("lisa@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new ChannelId("UCW6J17hZ_Vgr6cQgd_kHt5A"),
                    getTagSet("classmates"), new Favourite("false")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new ChannelId("UCctXZhXmG-kf3tlIXgVZUlw"),
                    getTagSet("colleagues"), new Favourite("false"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
