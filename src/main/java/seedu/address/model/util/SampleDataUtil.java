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
                new Person(new Name("Felix Arvid Ulf Kjellberg"),
                        new Phone("87438807"),
                        new Email("pewdiepie@example.com"),
                        new Address("5 Bedford Pl, Brighton BN1 2PT, UK"),
                        new ChannelId("UC-lHJZR3Gqxm24_Vd_AJ5Yw"),
                        getTagSet("Gaming", "Comedy"),
                        new Favourite("false")),
                new Person(new Name("Anthony Padilla"),
                        new Phone("93210283"),
                        new Email("anthonypadilla@example.com"),
                        new Address("4528 Olympiad Way, Sacramento, California"),
                        new ChannelId("UCPJHQ5_DLtxZ1gzBvZE99_g"),
                        getTagSet("Comedy"),
                        new Favourite("false")),
                new Person(new Name("Soren Wood"),
                        new Phone("99272758"),
                        new Email("zfsovietwomble@example.com"),
                        new Address("36 Selborne Rd, Hove BN3 3AH, UK"),
                        new ChannelId("UCQD3awTLw9i8Xzh85FKsuJA"),
                        getTagSet("Gaming", "Comedy"),
                        new Favourite("false")),
                new Person(new Name("Aamir"),
                        new Phone("91031282"),
                        new Email("zfcyanide@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new ChannelId("UCuHQqPko8f0lZtc8D2Z7BoA"),
                        getTagSet("Gaming", "Comedy"),
                        new Favourite("false")),

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
