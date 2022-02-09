# How to start the service

For this demo we used Ubuntu, so there is a bash script to start the services

- If you need to install docker. [Here is the tutorial](https://docs.docker.com/engine/install/ubuntu/)
- If you are using Windows you may need to install Docker Desktop. [Here](https://docs.docker.com/desktop/windows/install/)

All you need to do is: access the app folder (where you cloned it), using the terminal, and run the following command:

    bash startup.sh

if you are using an OS that is not linux-based, or if you have problems with `startup.sh`, 
you can run manually. To do so you need to run these following commands:

    ./gradlew clean build

    docker-compose up
    
if you are using intellij you can run it inside the terminal of the IDE.

# How to use the service

- We can access the endpoints, and test them, by accessing the following url, once the service is up:
  http://localhost:8080/swagger-ui.html


### First we need to create our user.
- Using the `/user/signUp` endpoint
- Here is an example of payload:
    - *We are creating an ADMIN*


```
{
    "email": "test@mail.com",
    "name": "test",
    "password": "pass",
    "role": "ADMIN"
}
```

- After signing up we should signIn
    - Using the `/user/signin` endpoint
    - This is how the payload is (you should use your data):

```
{
  "email": "test@mail.com",
  "password": "pass"
}
```

### After signing in we need to get the token that it generates to use for another requests

 ---

### Now you can create and delete some drinks and toppings,
#### *IF YOU ARE AN ADMIN*
- Creating
    - you need to post in the `/drinks` endpoint
    - You also need to pass your `token` in the requestParam field
    - Here is an example of payload:


```
{
  "name": "water",
  "price": 1
}
```

- Deleting
    - you need to use the endpoint `/drinks/{id}`.
      and send a `delete` request instead of `post`


### To create and delete toppings the process is the same, just change drinks for toppings in the endpoint:
- `post` -> `/toppings` = create
- `delete` -> `/toppings/{id}` = delete
---
### Using the cart

- you need to send a `post` to `/cart/add` to include products to cart
    - you need to send the `body` and the `token`
    - this is an example of payload:

```
{
  "quantity": 1,
  "drink": {
    "id": 1,
    "name": "water",
    "price": 1
  },
  "toppings": [
    {
      "id": 1,
      "name": "milk",
      "price": 1
    },
    {
      "id": 2,
      "name": "Lemon",
      "price": 1
    }
  ]
} 
```
*After adding a product, the return will show you the complete cart with the total cost
and the cost after applying the discount*


- If you need to delete an item of your cart you need to send a `delete`
  request to `/cart/delete-item/{itemId}`
    - you also need to send the `token`

- If you need to update and item you need to send a `post`
  to `/cart/update-item/` and send the body of your drink
- and also you send the id of you cart item
- and, as always, you need to send the `token`
- here is an example

```
{
  "id": 1,
  "drink": {
    "id": 1,
    "name": "water",
    "price": 1
  },
  "toppings": [
    {
    "id": 1,
    "name": "milk",
    "price": 1
    }
  ],
  "quantity": 2,
}
```