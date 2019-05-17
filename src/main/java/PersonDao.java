import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDao implements Dao<Person> {

    private List<Person> persons = new ArrayList<>();

    public PersonDao() {
        persons.add(new Person());
    }

    @Override
    public Optional<Person> get(long id) {
        return Optional.ofNullable(persons.get((int) id));
    }

    @Override
    public List<Person> getAll() {
        return persons;
    }

    @Override
    public void save(Person person) {
        persons.add(person);
    }

    @Override
    public void update(Person person, String[] params) {

    }

    @Override
    public void delete(Person person) {
        persons.remove(person);
    }

}
