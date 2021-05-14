# Úvod
Program je vytvorený v jazyku Java a hlavná myšlienka je BDD (binary decision diagram), kt. pozostáva z atribútov: <br>
* int inputSize
* int numberOfNodes
* int maxDepth
* Node root<br>
A Obsahuje hlavné metódy: <br>
* BDD BDD_create(BF bfunkcia)
* int BDD_reduce(BDD bdd)
* char BDD_use(BDD bdd, String vstupy)
<br>
Ďalšími štruktúrami programu sú triedy BF a Node. <br>
Trieda BF reprezentuje Booleovskú funkciu pomocou vektora za pomoci stringu. <br>
Trieda node reprezentuje jeden uzol BDD, pretože ten v je v programe reprezentovaný pomocou binárneho stromu. Každý node obsahuje hĺbku, danú value a referencie na pravého a ľavého potomka a rovnako tak aj referenciu na rodiča. Referencia na rodiča sa využíva pri redukovaní duplikovaných nodes. <br>
Posledná časť programu je tester (trieda Main), kt. náhodne vytvorí vstupný vektor, vytvorí diagram, zredukuje zduplikované nodes a následne otestuje všetky možné kombinácia vstupných premenných. S tým, že sa vyhodnocujú časy pre jednotlivé operácie ako aj percentuálna úspešnosť redukovania. <br>

# Stručný opis algoritmu
## BDD_creat
Samotné vytváranie stromu prebieha v pomocnej rekurzívnej metóde, kt. je zložená z dvoch if podmienok. Prvá if podmienka slúži pre ľavého potomka a funguje tak, že sa vytvorí nový string, kt. bude obsahovať ľavú polovicu stringu rodiča, nastaví sa pointer na rodiča a ďalej nasleduje rekurzívne volanie. Rovnako funguje aj druhý if pre pravého potomka. Funkcia do každého nodu nastaví aj jeho príslušnú hĺbku a nastaví sa aj maximálna hĺbka pre daný strom. <br>

## BDD_reduce
Ako prvé program podľa maximálnej hĺbky príslušného diagramu (stromu) vezme všetky listy a zredukuje ich tak aby sme mali vždy iba jeden node pre 1 a jeden node pre 0. To sa dosiahne tak, že prvá nájdená 1 a 0 sa zvolí ako vzor a všetky ostatné sa nato iba presmerujú. Už tento krok zabezpečí zredukovanie stromu o cca 50% pretože vrstva s listami je najväčšia a tvorí približne polovicu diagramu. <br>

Ďalšie redukovanie prebieha smerom zdola hore (od listov ku koreňu) čo je menej efektívne ako keby redukujeme smerom od koreňa avšak nakoľko je potrebné počítať koľko nodes sa odstráni je potrebné redukovať postupne po jednom. <br>
Následne sa teda postupne prechádza od najhlbších vrstiev až po koreň. V každej vrstve sú skontrolované všetky príslušné nodes a porovnané každý s každým. Ak sa nájde duplikát tak sa pomocou referencie na rodiča zmenia referencie na potomkov tak aby jeden z dvojice duplikátov zanikol a tým, že zanikne referencia na duplikát sa už Java garbage collection postará o zvyšok. <br>

## BDD_use
Veľmi jednoducho prechádza diagramom (stromom) podľa hodnoty príslušných premenných. Ak je premenná 0 ide sa vľavo ak je premenná 1 ide sa vpravo. Takto sa postupne vyhodnocujú všetky premenné avšak nato aby nastalo vyhodnotenie musí vždy hĺbka súhlasiť s poradím premennej na vstupe aby bolo zabezpečené, že sa vyhodnocuje správna premenná.
