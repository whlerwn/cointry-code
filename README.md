  Search for country name by phone number

To compile the application write via command line:
  ./gradlew build 

To run the tests write via command line:
  ./gradlew test

To run the application write via command line:
  ./gradlew bootRun

The application is running on port 8088.

To get a response from the server, send a POST request with JSON body with “number” as the key and the phone number to be identified as the value. 

Or open the HTML page (src/main/resources/static/home.html) and use client interface.
