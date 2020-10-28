# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet

# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)
Vi har brukt git til å dokumentere arbeidet mitt. 

Jeg har x commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1: løste oppgaven ved å lese kildekode som ble limt inn, tolket at foreldrereferanse måtte legges inn når ny node lages.

* Oppgave 2: løste oppgaven med å lage hjelpevariabel som skulle holde tellinga på antall forekomster av en gitt verdi. 
ved å iterere gjennom treet ved hjelp av compare-metoden navigerer vi oss til venstre eller høyre og der den gitte verdien er lik nodens verdi så vil compar-metoden go oss 0 og deretter øker vi vår hjelpevariabel med en.

* Oppgave 3:
    - førstePostorden: navigerer oss til første bladnode i treet mot venstresiden
    - nestePostorden: håndterer at når p er siste postorden, altså rot, retunerer metoden null. Hvis noden sin høyre er like noden vi sender vil de si at nodene har like verdi.Dermed retunerer vi noden til høyre.
    Videre lager vi en hjelpevariabel for forelder, deretter sjekker vi om foreldre noden har et eksisterende høyre barn eller om høyre noden har samme verdi som vi sender inn. vi lager en hjelpevariabel for neste postorden, og traverserer gjennom venstrebarna til gitt node og sjekker deretter om det eksisterer noen høyre barn. Hvis det eksisterer, så traverserer vi til vi treffer bladnoden som er vår neste postorden og returnerer denne.
    
* Oppgave 4:
    - postOrden: vi finner første posten ved å bruke metoden vi lagde i oppgave 3, og utfører en oppgave ved å bruke nodens verdi. Deretter vi bruker vi en while-løkke for å finne neste postorden, bruker metoden laget i oppgave 3, utfører oppgave. Går ut av løkken når siste node i postorden, som er roten.
    - postordenRecursive: Håndterer hvis p er null så skal treet ikke travaseres. Lager en rekursiv-metode som kaller på seg selv og sender inn nodens venstre og høyre barn.