package de.com.magnojr.dao;


import de.com.magnojr.domain.Transfer;
import de.com.magnojr.dto.TransferResponseDTO;
import org.jdbi.v3.core.statement.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static de.com.magnojr.main.GenericBankService.handle;

public class TransferDAO {

    public void save(Transfer transfer) {
        handle.useTransaction(h -> {
            h.createUpdate("INSERT INTO TRANSFER(id, date, value, from_account_id, to_account_id) " +
                    "VALUES (?,?,?,?,?)")
                    .bind(0, transfer.getId())
                    .bind(1, transfer.getDate())
                    .bind(2, transfer.getValue())
                    .bind(3, transfer.getFromAccount().getId())
                    .bind(4, transfer.getToAccount().getId())
                    .execute();

            h.createUpdate("UPDATE ACCOUNT " +
                    "SET AMOUNT = ?" +
                    "WHERE ID = ?")
                    .bind(0, transfer.getFromAccount().getAmount())
                    .bind(1, transfer.getFromAccount().getId())
                    .execute();

            h.createUpdate("UPDATE ACCOUNT " +
                    "SET AMOUNT = ?" +
                    "WHERE ID = ?")
                    .bind(0, transfer.getToAccount().getAmount())
                    .bind(1, transfer.getToAccount().getId())
                    .execute();
        });
    }

    public List<TransferResponseDTO> listTransferResponsesDTO(Optional<LocalDate> date) {

        Query query = buildQueryForTransferResponseDTO(date);
        return query
                .mapToBean(TransferResponseDTO.class)
                .list();

    }

    private Query buildQueryForTransferResponseDTO(Optional<LocalDate> date) {
        String sql = "SELECT   " +
                "T.ID id,         " +
                "T.DATE dateTime, " +
                "T.VALUE value,   " +
                "T.from_account_id fromAccount, " +
                "T.to_account_id toAccount      " +
                "FROM TRANSFER T  " +
                "WHERE 1=1 ";
        Query query;
        if (date.isPresent()) {
            sql += "AND T.DATE BETWEEN ? AND ? ";
            query = handle.createQuery(sql)
                    .bind(0, date.get().atStartOfDay())
                    .bind(1, date.get().atTime(LocalTime.MAX));
        } else {
            query = handle.createQuery(sql);

        }
        return query;
    }

}
