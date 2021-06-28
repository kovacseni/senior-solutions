# Gyakorló feladat - Ó! Ió! Ció! Oltásregisztráció!

A feladat egy olyan egyszerű webalkalmazás létrehozása, ami a Covid-19 oltással
kapcsolatos feladatok háttéradminisztrációját biztosítja számunkra.
A feladatnak része a tervezés is, találd ki, hogy milyen osztályokra lesz szükséged,
azok hogyan épüljenek egymásra, a tanult konvenciók és best practice-ek használatán
felül a tervezésben és megvalósításban szabad kezet kapsz.

## Leírás

Az alkalmazásnak az alábbi feladatokat kell tudni teljesíteni:

* Lehet regisztrálni oltásra, ehhez meg kell adni a nevet, TAJ-számot, születési időt,
  születési helyet és a lakcímet.
* Lehet ellenőrizni, hogy valaki regisztrálva van-e oltásra, ehhez meg kell adni az
  illető TAJ-számát, születési idejét és születési helyét
* Lehet időpontot foglalni oltásra, ehhez meg kell adni a TAJ-számot, az oltás fajtáját
  és az oltás helyét és idejét.
* Opcionális feladatként le lehet kérdezni, hogy egy adott személy oltva van-e, ehhez
  meg kell adni az illető TAJ-számát, születési helyét és idejét, és a metódus térjen
  vissza azzal, hogy a személy kapott-e már oltást, ha igen, hány adagot, mikor kapta
  ezeket, és melyik fajtából
  
## Architektúra

Az alkalmazás kövesse a háromrétegű architektúrát, legyenek elkülönítve a rétegek feladatai.
Most nem kell azzal foglalkozni, hogy ha a név és a születési hely bevitelénél valaki
elrontja a kis- és nagybetűk használatát (bármilyen irányba), akkor történjen-e meg
automatikusan a hibás formátum javítása. (De opcionálisan természetesen meg lehet oldani
ezt is. Ebben az esetben ez legyen a Service réteg feladata.)
A perzisztencia rétegnek nem kell valódi adatbázist elérnie, használj valamilyen Java-s
adatstruktúrát a Repository osztályokban. 