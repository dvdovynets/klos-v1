# KLOS REST API
The simple server app designed to simplify the organisation of Kyiv Last One Standing Race.

The application consists of 6 controllers that are responsible for CRUD operations with the 5 main entities.

For the database Postgresql is used.

Application uses JWT authentication and also has SWAGGER for api information and testing.

After application started the 2 mane roles will be created (USER and ADMIN). 
Then just add the Event and we are good to go.

You need to register and then sign up for a particular event. This will create a line in Result table.
This table will have new line for each participant for each event.
All calculated data for event will be stored there, as well as participant status.
Each participant starts with 'DNS' status (Does Not Start) and after completion of first lap the status 
will change to 'RUNNING'. 
When participant could finish the lap during 60 minutes, the status will be changed to 'DNF' (Does Not Finish).
And when the last participant will finish the lap hi/she will be running alone, the status will be changed to 'WINNER'.

Every participant will have a bracelet with NFC tag in it, that will be connected to particular row in Result table.
According to this implementation the android app will be scanning those tag after each lap completion and will send
data to a Lap controller. 
After each new lap received the statistics fields in Result table will be automatically updated.

