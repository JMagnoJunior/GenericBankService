
This is small test with light tools: javalin + java + jdbi


Endpoints:
----
  * /transfer
     * GET /transfer(?date=2019-12-12)  - lists all transfers (is possible to filter by date)
     * POST /transfer - transfer money between accounts. 
     
     body:
     ```
       {
     	"from" : "fbd22a74-b895-4ed7-bcda-2e132fa450d5",
     	"to" : "ddd6c4ea-d56d-4348-b326-917e14d0ee2c",
     	"value": "500"
       }
    ```
            
    body:             
  * /account
    * GET  /account      - list all accounts
    * GET  /account/:id  - get an account data by id
    * POST /account      - create a new Account 
    ```
      {
   	    "amount": "1000",
        "user": {
            "name": "new user 3"
        }
      }
   
How to use:
----

Get the file: \
https://drive.google.com/open?id=1-PA_q3JDLer60023j_3OBaHVSPT0o6fi \
\
Run the jar: \
`java -jar generic-bank-service-1.0-SNAPSHOT-jar-with-dependencies.jar`


On startup time a small script will create a in-memory database with 2 accounts.
To list these accounts try: \
`curl http://localhost:9090/account`

to transfer money between accounts:
`curl -d '{"from" : "fbd22a74-b895-4ed7-bcda-2e132fa450d5", "to" : "ddd6c4ea-d56d-4348-b326-917e14d0ee2c", "value": "500"}' -H "Content-Type: application/json" -X POST http://localhost:9090/transfer`
  
   
 