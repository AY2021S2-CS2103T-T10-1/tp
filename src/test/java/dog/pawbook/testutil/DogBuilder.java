package dog.pawbook.testutil;

import dog.pawbook.model.managedentity.dog.Breed;
import dog.pawbook.model.managedentity.dog.DateOfBirth;
import dog.pawbook.model.managedentity.dog.Dog;
import dog.pawbook.model.managedentity.dog.Sex;

/**
 * A utility class to help with building Dog objects.
 */
public class DogBuilder extends EntityBuilder<DogBuilder> {

    public static final String DEFAULT_BREED = "Golden Retriever";
    public static final String DEFAULT_DATEOFBIRTH = "10-1-2020";
    public static final String DEFAULT_SEX = "male";
    public static final int DEFAULT_OWNERID = 1;

    private Breed breed;
    private DateOfBirth dob;
    private Sex sex;
    private int ownerID;

    /**
     * Creates a {@code DogBuilder} with the default details.
     */
    public DogBuilder() {
        super();
        breed = new Breed(DEFAULT_BREED);
        dob = new DateOfBirth(DEFAULT_DATEOFBIRTH);
        sex = new Sex(DEFAULT_SEX);
        ownerID = DEFAULT_OWNERID;
    }

    /**
     * Initializes the DogBuilder with the data of {@code dogToCopy}.
     */
    public DogBuilder(Dog dogToCopy) {
        super(dogToCopy);
        breed = dogToCopy.getBreed();
        dob = dogToCopy.getDob();
        sex = dogToCopy.getSex();
        ownerID = dogToCopy.getOwnerId();
    }

    /**
     * Sets the {@code Breed} of the {@code Dog} that we are building.
     */
    public DogBuilder withBreed(String breed) {
        this.breed = new Breed(breed);
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code Dog} that we are building.
     */
    public DogBuilder withDateOfBirth(String dob) {
        this.dob = new DateOfBirth(dob);
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code Dog} that we are building.
     */
    public DogBuilder withSex(String sex) {
        this.sex = new Sex(sex);
        return this;
    }

    /**
     * Sets the {@code OwnerID} of the {@code Dog} that we are building.
     */
    public DogBuilder withOwnerID(int ownerID) {
        this.ownerID = ownerID;
        return this;
    }

    public Dog build() {
        return new Dog(name, breed, dob, sex, ownerID, tags);
    }

}
