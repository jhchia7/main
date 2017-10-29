package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.ChannelId;
import seedu.address.model.person.Email;
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
                           new Email("pewdiepie@gmail.com"),
                    new Address("5 Bedford Pl, Brighton BN1 2PT, UK"), new ChannelId("UC-lHJZR3Gqxm24_Vd_AJ5Yw"),
                    getTagSet("Gaming", "Comedy")),
                new Person(new Name("Soren Wood"), new Phone("99272758"), new Email("zfsovietwomble@gmail.com"),
                    new Address("36 Selborne Rd, Hove BN3 3AH, UK"), new ChannelId("UCQD3awTLw9i8Xzh85FKsuJA"),
                    getTagSet("Gaming", "Comedy")),
                new Person(new Name("Anthony Padilla"), new Phone("93210283"), new Email("contact@apadilla.co"),
                    new Address("4528 Olympiad Way, Sacramento, California"), new ChannelId("UCPJHQ5_DLtxZ1gzBvZE99_g"),
                    getTagSet("Comedy")),
                new Person(new Name("Aamir"), new Phone("91031282"), new Email("zfcyanide@gmail.com"),
                            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                            new ChannelId("UCuHQqPko8f0lZtc8D2Z7BoA"),
                    getTagSet("Gaming", "Comedy")),
                new Person(new Name("Lisa Gade"), new Phone("92492021"), new Email("lisa@mobiletechreviews.com"),
                    new Address("440 Old Iron Works Rd Spartanburg, SC 29302 USA"),
                        new ChannelId("UCW6J17hZ_Vgr6cQgd_kHt5A"),
                    getTagSet("Technology")),
                new Person(new Name("Gary Vaynerchuk"), new Phone("92624417"), new Email("contact@garyvaynerchuk.com"),
                    new Address("1170 Angelo Dr, Beverly Hills, CA 90210, USA"),
                        new ChannelId("UCctXZhXmG-kf3tlIXgVZUlw"),
                    getTagSet("People")),
                new Person(new Name("Edberg Oliveiro"), new Phone("92624417"), new Email("zfedberg@gmail.com"),
                    new Address("Domaregatan 21, 824 43 Hudiksvall, Sweden"), new ChannelId("UCvlvI5s742g4cJiXS_ZYdfQ"),
                    getTagSet("Gaming", "Comedy"))
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
