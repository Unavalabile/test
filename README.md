# test

Selenium + TestNG (Page Object) pentru formularul demoqa. Implicit rulează pe un browser **remote**.

```bash
# remote (implicit)
docker compose -f docker-compose.grid.yml up -d     # Selenium standalone (Mac Apple Silicon ok)
mvn clean test -Dselenoid.url=http://localhost:4444/wd/hub

# sau Selenoid
docker compose up -d
mvn clean test -Dbrowser.version=128.0

# Chrome local pe Mac
mvn clean test -Dremote=false
mvn clean test -Dremote=false -Dheadless=true
```

Proprietăți: `-Dremote`, `-Dheadless`, `-Dselenoid.url`, `-Dbrowser.version`, `-Dchrome.binary`, `-Dkeep.open`.
