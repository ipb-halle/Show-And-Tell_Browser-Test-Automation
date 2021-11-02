# Browser tests dockerized in a Selenoid environment

* Download the [Configuration Manager](https://aerokube.com/cm/latest/)
* make the binary executeable if necessary
* run `./cm selenoid start --vnc` (pulls all necessary images including the two latest versions of Firefox, Chrome and Opera) and `./cm selenoid-ui start` (nice frontend)
* frontend: [http://localhost:8080](http://localhost:8080)
* Selenoid's configuration is in `~/.aerokube/selenoid/`.

Run tests with maven: `mvn clean test`