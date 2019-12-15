package de.com.magnojr.dao;

import de.com.magnojr.domain.Account;
import de.com.magnojr.domain.User;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static de.com.magnojr.main.GenericBankService.handle;

public class AccountDAO {

    private static String SQL_SELECT_ACCOUNT = "SELECT     " +
            "   A.ID ACCOUNT_ID, " +
            "   A.AMOUNT AMOUNT, " +
            "   U.ID USER_ID,    " +
            "   U.NAME USER_NAME " +
            " FROM ACCOUNT A     " +
            " INNER JOIN USER U ON A.USER_ID = U.ID " +
            " WHERE 1=1 ";
    private static String SQL_AND_ID = " AND A.id = ?  ";

    public Account findById(UUID id) {

        Account account = handle.
                createQuery(SQL_SELECT_ACCOUNT + SQL_AND_ID)
                .bind(0, id)
                .map(this::buildAccount)
                .findOnly();

        return account;
    }

    public List<Account> findAll() {
        return handle.createQuery(SQL_SELECT_ACCOUNT)
                .map(this::buildAccount)
                .list();
    }

    public void save(Account account) {

        handle.useTransaction(
                h -> {
                    h.createUpdate("INSERT INTO USER(id, name) VALUES " +
                            "(?,?)")
                            .bind(0, account.getUser().getId())
                            .bind(1, account.getUser().getName())
                            .execute();
                    h.createUpdate("INSERT INTO ACCOUNT(id, amount, user_id) VALUES " +
                            "(?,?,?)")
                            .bind(0, account.getId())
                            .bind(1, account.getAmount())
                            .bind(2, account.getUser().getId())
                            .execute();
                }
        );
    }

    private Account buildAccount(ResultSet rs, StatementContext cx) throws SQLException {

        User user = User.builder()
                .id(UUID.fromString(rs.getString("USER_ID")))
                .name(rs.getString("USER_NAME"))
                .build();

        return Account.builder()
                .id(UUID.fromString(rs.getString("ACCOUNT_ID")))
                .amount(rs.getString("AMOUNT"))
                .user(user)
                .build();
    }
}
