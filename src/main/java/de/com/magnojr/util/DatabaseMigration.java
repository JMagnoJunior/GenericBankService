package de.com.magnojr.util;

import org.jdbi.v3.core.Handle;

public class DatabaseMigration {

    public static void populateDatabase(Handle handle) {
        handle.execute("create table USER (id UUID  not null PRIMARY KEY, name VARCHAR(100) not null)");
        handle.execute("create table ACCOUNT (id UUID not null PRIMARY KEY,amount VARCHAR(100) not null, user_id UUID unique REFERENCES USER(id))");
        handle.execute("create table TRANSFER (id UUID not null PRIMARY KEY,date TIMESTAMP not null,value VARCHAR(255) not null,from_account_id UUID REFERENCES ACCOUNT(id),to_account_id  UUID REFERENCES ACCOUNT(id))");
        handle.execute("INSERT INTO USER ( ID, NAME) VALUES ('b23df771-624b-43d3-8f6d-8784a80a4af4', 'USER-1')");
        handle.execute("INSERT INTO USER ( ID, NAME) VALUES ('c9df274e-4bae-4a54-ac11-727de8eb5f1b', 'USER-2')");
        handle.execute("INSERT INTO ACCOUNT (ID, AMOUNT, USER_ID) VALUES ('fbd22a74-b895-4ed7-bcda-2e132fa450d5', '1000', 'b23df771-624b-43d3-8f6d-8784a80a4af4'), ('ddd6c4ea-d56d-4348-b326-917e14d0ee2c', '50000', 'c9df274e-4bae-4a54-ac11-727de8eb5f1b')");

    }

}
