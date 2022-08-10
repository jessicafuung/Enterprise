# PG5100 Enterprise Programming - Final Exam Documentation

## PostgreSQL - Database
### Tables
* users
* authorities
* users_authorities
* orders (should have named it deliveries...)
* customers
* items
* item_orders

## Starting point - Login
* Login via `/api/authentication`. Choose between two authority levels:
  * As 'USER':
    ````json
          {
            "username":"user@user.com",
            "password":"burrito"
          }
  * as 'ADMIN':
      ````json
          {
            "username":"admin@admin.com",
            "password":"pirate"
          }

## Endpoints
    * Hva skal man forvente å få ut av de

###/ GET
  * [x] Retrieve all deliveries go to `/api/delivery/all`
  * [x] Retrieve a single delivery by order ID go to `/api/delivery/get/{orderId}` (eg /api/delivery/get/1 )
  * [x] Retrieve all authority levels go to `/api/authority/all` WARN: Only admin has authority.
  * [x] Retrieve all users go to `/api/users/all` WARN: Only admin has authority.

###/ POST
  * [x] Add a new delivery with customer and order information go to `/api/delivery/register`
    * e.g how to post via Postman:
      ````json
      {
         "email":"test@test.com",
         "address":"donald duck",
         "phone":"4654654354",
         "deliveryDate":"2022-08-30"
      }   

###/ PUT
  * [x] Update a customer's information go to `/api/delivery/update/{customerId}`
    * e.g how to update via Postman:
      ````json
      {
         "id":3, 
         "email":"cake@cake.com",
         "address":"snacks street 123",
         "phone":"12345678"
      }

###/ DELETE
  * [x] Delete a delivery go to `/api/delivery/delete/{orderId}`. WARN: Only admin has authority.
  

## Tests
* Integration tests:
  * DatabaseIntegrationTests
  * FullSystemTest

* Unit tests:
  * AuthControllerUnitTest
  * UserServiceUnitTest

## Troubleshooting
I could not figure out why my test failed in `AuthControllerUnitTest` and I tried to Google it. It looks like it might be 
a version error (nothing wrong with the code), but I am not sure. 
`java.lang.IllegalStateException: Failed to load ApplicationContext` - the error 
when I run the test class. I leave the class be there, but you might as well delete it because maven is angry about it :(


## Conclusion
I reread the tasks and saw a little too late that 'items' in delivery/ order class should show WITH quantities (I don't know why I did not catch that earlier...) 
At this point I do not have enough time to fix that.. 
If I had more time I would add quantities to the items and maybe created a 
better database structure (even though I spent some time figuring that part).
I would also started the testing much earlier... I did not had enough time to do all the
testing I wanted because I spent a lot of time to troubleshooting. 
Added more functionalities using / PATCH :)# Enterprise
