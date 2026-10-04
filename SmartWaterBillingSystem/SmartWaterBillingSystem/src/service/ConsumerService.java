package service;

import model.CommercialConsumer;
import model.Consumer;
import model.ResidentialConsumer;
import repository.ConsumerRepository;
import util.ConsumerNotFoundException;
import util.DuplicateConsumerException;
import util.FileManager;
import util.InvalidInputException;
import util.Validation;

import java.util.List;


public class ConsumerService {

    private final ConsumerRepository repository;

    public ConsumerService(ConsumerRepository repository) {
        this.repository = repository;
    }

    public Consumer addResidentialConsumer(String name, String address, String phone)
            throws InvalidInputException {
        Validation.validateName(name);
        Validation.validateAddress(address);
        Validation.validatePhone(phone);

        Consumer consumer = new ResidentialConsumer(name.trim(), address.trim(), phone.trim());
        try {
            repository.add(consumer);
        } catch (DuplicateConsumerException e) {
            throw new InvalidInputException(e.getMessage());
        }
        FileManager.logAction("Added residential consumer #" + consumer.getConsumerId());
        return consumer;
    }

    public Consumer addCommercialConsumer(String name, String address, String phone)
            throws InvalidInputException {
        Validation.validateName(name);
        Validation.validateAddress(address);
        Validation.validatePhone(phone);

        Consumer consumer = new CommercialConsumer(name.trim(), address.trim(), phone.trim());
        try {
            repository.add(consumer);
        } catch (DuplicateConsumerException e) {
            throw new InvalidInputException(e.getMessage());
        }
        FileManager.logAction("Added commercial consumer #" + consumer.getConsumerId());
        return consumer;
    }

    public Consumer searchById(int id) throws ConsumerNotFoundException {
        return repository.findById(id);
    }

    public List<Consumer> searchByName(String namePart) {
        return repository.findByName(namePart);
    }

    public Consumer updateConsumer(int id, String newAddress, String newPhone)
            throws ConsumerNotFoundException, InvalidInputException {
        Validation.validateAddress(newAddress);
        Validation.validatePhone(newPhone);

        Consumer consumer = repository.findById(id);
        consumer.setAddress(newAddress.trim());
        consumer.setPhone(newPhone.trim());
        FileManager.logAction("Updated consumer #" + id);
        return consumer;
    }

    public void deleteConsumer(int id) throws ConsumerNotFoundException {
        repository.delete(id);
        FileManager.logAction("Deleted consumer #" + id);
    }

    public List<Consumer> listAll() {
        return repository.findAll();
    }

    public int totalConsumers() {
        return repository.count();
    }

    
    public String buildBackupDump() {
        StringBuilder sb = new StringBuilder();
        for (Consumer c : repository.findAll()) {
            sb.append(c.toDataLine()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /** Rebuilds the repository from lines previously written by buildBackupDump(). */
    public void loadFromBackup(List<String> lines) {
        for (String line : lines) {
            try {
                String[] parts = line.split("\\|", -1);
                if (parts.length < 5) {
                    continue;
                }
                int id = Integer.parseInt(parts[0].trim());
                String type = parts[1].trim();
                String name = parts[2].trim();
                String phone = parts[3].trim();
                String address = parts[4].trim();

                Consumer c = type.equalsIgnoreCase("Commercial")
                        ? new CommercialConsumer(id, name, address, phone)
                        : new ResidentialConsumer(id, name, address, phone);
                repository.add(c);
            } catch (Exception e) {
                System.err.println("Skipping malformed backup line: " + line);
            }
        }
    }
}