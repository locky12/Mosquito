#!/bin/bash
echo "*** Compilation ***"
javac -d build src/moskitoAttack/*.java
echo "*** Compilation terminee ***"
read -p "Appuyez pour continuer"
echo "*** Execution ***"
java -cp build moskitoAttack.Main
echo "*** Fin du programme ***"

