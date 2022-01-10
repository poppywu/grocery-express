
### Focus Area

- Archivability
- Security
- Auditability

### 1. To build the application, run from the root directory:

```
docker compose up
```

### 2. Interact with the app through [Postman](https://www.postman.com/downloads/).
- ```POST``` - will send POST request
- ```GET``` - will send GET request
- The sample request payloads are defined for each API endpoint, and success and failure response are saved as examples.
- Import the `Grocery\ V2.postman_collection.json` config within Postman to view the saved requests (Import -> File).

### 3. Authentication and Authorization
- ```POST /login``` with request body ```{"username":"taylor","password":"1234"}``` will return a response header with ```access_token```
- You can save this token to the "Grocery V2" collection's Authorization header as a ```Bearer Token```. This will log you in as ```ROLE_ADMIN```for all requests in the collection.
- You can also save the token for a specific ```/private/**``` request to test an individual endpoint.

### 4. View Logs
- ```POST /private/logs``` as login Admin user

### 5. Data Encryption
- Customer credit and Pilot taxId are encrypted in the tables ```customer``` and ```pilot```, respectively.
- App User password is encrypted in the table ```app_user```

### 6. Database backup and restore
#### To back up the database
```mysqldump -u root -p test grocery_store > dump.sql```
#### To restore the database
```mysql -u root -p test grocery_store < dump.sql```
