<h1 align="center">Top Trumps | MadStax</h1> 

This project is being developed as part of the University of Glasgow MSc in Software Development team project. It consists of a Top Trumps game application written in Java.

The application itself is broken down into two parts: the online mode; and command line mode. 

### Running the project

1. Select your preferred directory and clone this repo <br> 
    ```git clone ${this_repo}```<br><br>
2. Enter the project directory<br>
```cd uofg-msc-team-project```<br><br>
3. Make sure you have ```Maven``` properly installed in your environment. Then, simply generate the ```.jar``` as illustrated below<br> 
```mvn package```<br><br>
4. ```java -jar target/TopTrumps.jar -c``` for the **CLI** mode<br>
or<br>
```java -jar target/TopTrumps.jar -o``` for the **online** mode<br>
5. If you're using the online mode, open your internet browser and connect to http://localhost:7777/toptrumps to play the game!<br>
6. Enjoy! 




 