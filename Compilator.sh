#!/bin/bash
javac -cp bin -d bin -encoding UTF-8 src/effet/effet_nocif/*.java
javac -cp bin -d bin -encoding UTF-8 src/effet/effet_benefique/*.java
javac -cp bin -d bin -encoding UTF-8 src/effet/effet_tour/*.java
javac -cp bin -d bin -encoding UTF-8 src/effet/*.java
javac -cp bin -d bin -encoding UTF-8 src/rune/*.java
javac -cp bin -d bin -encoding UTF-8 src/stat/*.java
javac -cp bin -d bin -encoding UTF-8 src/personnage/*.java
javac -cp bin -d bin -encoding UTF-8 src/autre/*.java
javac -cp bin -d bin -encoding UTF-8 src/application/*.java
