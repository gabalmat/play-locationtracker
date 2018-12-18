# NCSU CSC 750 (Service Oriented Computing)

Project 1

Play framework server application for Android application that tracks a user's
location during outdoor activities (walking, running, or cycling). The Android application 
constantly sends the user’s location to this server, which saves the location information 
in a database and tells the user how far he or she has gone. To save battery on the client, 
the server also tells the client how long to wait before sending the next update, based on 
the user’s speed.

The server accepts requests in JSON format, saves the location information in an SQL
database, and sends to the client a) total distance and b) how long the client should wait
before sending the next update.

For more details, see [Assignment Instructions](doc/Project1_assignment.pdf)