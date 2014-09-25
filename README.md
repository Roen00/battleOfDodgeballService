# Jak to uruchomić
###1. Wyciągnij repozytorium
Wywołaj w terminalu:

```
$ git clone https://github.com/Roen00/battleOfDodgeballService.git
```

###2. mongoDB

Pobierz paczkę dla swojego systemu:
```
http://www.mongodb.org/downloads
```

będąc w GŁOWNYM katalogu (tak, aby mieć pod sobą folder bin) z mongoDB wykonaj polecenie:
```
$ mkdir data
```

następnie uruchom bazę danych poleceniem:
```
$ bin/mongod --dbpath data --rest
```

###3. play framework

Wejdź do katalogu z aplikacją (tam gdzie dałeś clone).

następnie uruchom activatora:
```
$ ./activator
```
W oknie activatora wpisz:
```
eclipse
```

###4. eclipse
Wykonaj import do eclipsa.