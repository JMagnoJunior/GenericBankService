package de.com.magnojr.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.com.magnojr.dao.AccountDAO;
import de.com.magnojr.dao.TransferDAO;
import de.com.magnojr.services.AccountService;
import de.com.magnojr.services.IAccountService;
import de.com.magnojr.services.ITransferService;
import de.com.magnojr.services.TransferService;
import de.com.magnojr.util.DatabaseMigration;
import io.javalin.Javalin;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class GenericBankService {

    public static Handle handle;
    public static ITransferService transferService;
    public static IAccountService accountService;
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {

        handle = initDatabase();
        DatabaseMigration.populateDatabase(handle);
        Javalin app = Javalin.create().start(9090);
        setDefaultJsonSerialization();
        initServices();
        Routes.mapRoutes(app);

    }

    private static void initServices() {
        final AccountDAO accountDAO = new AccountDAO();
        final TransferDAO transferDAO = new TransferDAO();
        accountService = new AccountService(accountDAO);
        transferService = new TransferService(accountService, transferDAO);
    }

    private static Handle initDatabase() {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test:DB_CLOSE_DELAY=-1");
        return jdbi.open();
    }

    private static void setDefaultJsonSerialization() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
