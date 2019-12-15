package de.com.magnojr.main;

import de.com.magnojr.controllers.AccountController;
import de.com.magnojr.controllers.TransferController;
import io.javalin.Javalin;

class Routes {


    static void mapRoutes(Javalin app) {

        TransferController transferController = new TransferController(GenericBankService.transferService);
        AccountController accountController = new AccountController(GenericBankService.accountService);

        app.post(TransferController.PATH, transferController::transfer);
        app.get(TransferController.PATH, transferController::list);

        app.get(AccountController.PATH, accountController::list);
        app.get(AccountController.PATH + "/:accountId", accountController::get);
        app.post(AccountController.PATH, accountController::create);
    }
}
