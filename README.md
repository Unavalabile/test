# test

Selenium + TestNG (Page Object) pentru formularul demoqa.

```bash
docker compose -f docker-compose.grid.yml up -d        # pornește browserul remote
mvn clean test -Dbrowser.version= -Dselenoid.url=http://localhost:4444/wd/hub

mvn clean test -Dremote=false                          # Chrome local
mvn clean test -Dremote=false -Dheadless=true          # local, fără fereastră
```
