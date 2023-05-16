# Dice Roll Game API
This is a simple RESTful API for a game that consists of throwing two six-sided dice. If the result equals 7, the user wins; otherwise, they lose.

The API is built using Java Spring Boot and uses JSON Web Token for user registration and authentication through bearer token technology.

# API Endpoints
## Authentication
### Register a User
URL: /api/auth/register
Method: POST
Description: Register a new user.

The request must include an email and password in JSON format. The response will include a JSON Web Token that can be used for authentication in subsequent requests.

### Authenticate User
URL: /api/auth/authenticate
Method: POST
Description: Authenticate an existing user.

The request must include an email and password in JSON format. The response will include a JSON Web Token that can be used for authentication in subsequent requests.

## Player
### Add a Player
URL: /api/players
Method: POST
Description: Add a new player.

The request must include a name in JSON format. The response will include the player ID and the player name.

### Update Player
URL: /api/players/{id}
Method: PUT
Description: Update player details.

The request must include a new name in JSON format and specify the player ID in the URL. The response will include the updated player details.

## Game
### Create a Game
URL: /api/players/{id}/games
Method: POST
Description: Create a new game for a specific player.

The request will create a new game and return the game ID, the user name, the result of both dice, and a win or lose result.

### Get All Games
URL: /api/players/{id}/games
Method: GET
Description: Get all games for a specific player.

The request will return a list of all games that the player has played, including the game ID, the user name, the result of both dice, and a win or lose result.

## Ranking
### Get Best Player
URL: /api/players/ranking/winner
Method: GET
Description: Get the player with the highest success rate.

The request will return the player with the highest success rate, including the player name and the player's success rate.

### Get Worst Player
URL: /api/players/ranking/loser
Method: GET
Description: Get the player with the lowest success rate.

The request will return the player with the lowest success rate, including the player name and the player's success rate.

### Get Average Success Rate
URL: /api/ranking
Method: GET
Description: Get the average success rate of all players.

The request will return the average success rate of all players.

### Get All Players Ranked
URL: /api/players
Method: GET
Description: Get all players and their success rate.

The request will return a list of all players who have played the game, including the player name and the player's success rate.

## Authentication
All requests except for register and authenticate require authentication. 
Authentication is done using a JSON Web Token obtained from the register or authenticate endpoints. 
To authenticate a request, add an Authorization header to the request with the value Bearer <token>, where <token> is the JSON Web Token obtained from the register or authenticate endpoint.
  
## Error Handling
This API returns the following error responses:

- 200 OK if the request is successful.
- 404 Not Found if a good request is handled but the object is not found in the database.
- 418 I'm a teapot if there is a database access failure.
- 500 Internal Server Error if a good request returns null.



If an error occurs, the response will include an error message in the response body, along with the appropriate HTTP status code.

## Contributors
This app was created by Marçal Fargas. Contributions are welcome! If you find a bug or have a feature request, please submit an issue or a pull request.

