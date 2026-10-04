import menu.Menu;
import repository.ConsumerRepository;
import service.BillingService;
import service.ConsumerService;
import util.FileManager;


public class Main {
    public static void main(String[] args) {
        ConsumerRepository repository = new ConsumerRepository();
        ConsumerService consumerService = new ConsumerService(repository);
        BillingService billingService = new BillingService();

        
        consumerService.loadFromBackup(FileManager.loadConsumerLines());
        System.out.println("Loaded " + consumerService.totalConsumers() + " consumer(s) from backup.");

        Menu menu = new Menu(consumerService, billingService);
        menu.run();

        
        FileManager.backupConsumersSync(consumerService.buildBackupDump());
        System.out.println("Data saved. Bye!");
    }
}