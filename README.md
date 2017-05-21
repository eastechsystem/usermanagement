# usermanagement
Q1. Network programming and Object-Oriented Design (General). Implemented Java JAX-RS (Restfull Web Services) for User Representation in json format data for User's friends and friend of friends.


=> Consider a RESTful Web API that returns a representation of a User in JSON. Write a program which, given a user ID (integer) as input, will output a list of the names of the user's friends and friends-of-friends.
   Write your solution as a program with an object oriented design. Your program will be evaluated on both it's design and correctness.

=> Web API Specification

=> Request Endpoint     
     -> /user/{identifier}     (#fetch user by id)
     -> /user/                 (#fetch all users)

=> Response Format
    The response will be in JSON with the following attributes:
    ●	id: user ID
    ●	name: user's name
    ●	friends: array of user IDs representing the user's friendships
=> Sample Response
    { 
      "id": 1, 
      "name": "Austin",
      "friends": [2, 5] 
    }

