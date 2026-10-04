package repository;

import model.Consumer;
import util.ConsumerNotFoundException;
import util.DuplicateConsumerException;

import java.util.ArrayList;
import java.util.List;


public class ConsumerRepository {

    private final List<Consumer> consumers = new ArrayList<>();

    public void add(Consumer consumer) throws DuplicateConsumerException {
        for (Consumer c : consumers) {
            if (c.getConsumerId() == consumer.getConsumerId()) {
                throw new DuplicateConsumerException(
                        "Consumer ID " + consumer.getConsumerId() + " already exists.");
            }
        }
        consumers.add(consumer);
    }

    public Consumer findById(int id) throws ConsumerNotFoundException {
        for (Consumer c : consumers) {
            if (c.getConsumerId() == id) {
                return c;
            }
        }
        throw new ConsumerNotFoundException("No consumer found with ID " + id);
    }

    public List<Consumer> findByName(String namePart) {
        List<Consumer> matches = new ArrayList<>();
        for (Consumer c : consumers) {
            if (c.getName().toLowerCase().contains(namePart.toLowerCase())) {
                matches.add(c);
            }
        }
        return matches;
    }

    public void delete(int id) throws ConsumerNotFoundException {
        Consumer target = findById(id);
        consumers.remove(target);
    }

    public List<Consumer> findAll() {
        return consumers;
    }

    public int count() {
        return consumers.size();
    }
}
