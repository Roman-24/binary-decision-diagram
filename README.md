# EN
The program is written in Java, and its main concept is BDD (Binary Decision Diagram), which consists of the following attributes:
* int inputSize
* int numberOfNodes
* int maxDepth
* Node root
It contains the main methods:
* BDD BDD_create(BF booleanFunction)
* int BDD_reduce(BDD bdd)
* char BDD_use(BDD bdd, String inputs)

Additional structures in the program are the classes BF and Node.
The BF class represents a Boolean function using a vector, which is implemented via a string.
The Node class represents a single node of the BDD, as it is represented in the program as a binary tree. Each node contains depth, a given value, and references to the left and right child, as well as a reference to the parent. The parent reference is used when reducing duplicated nodes.
The last part of the program is a tester (Main class), which randomly creates an input vector, generates a diagram, reduces duplicate nodes, and then tests all possible combinations of input variables. It evaluates the times for individual operations as well as the percentage success rate of the reduction.

## Brief Description of the Algorithm
### BDD_create
The tree creation process takes place in a helper recursive method, which consists of two `if` conditions. The first `if` condition is for the left child and works by creating a new string that will contain the left half of the parent string, sets a pointer to the parent, and then follows with a recursive call. The second `if` works similarly for the right child. The function also sets the corresponding depth for each node and sets the maximum depth for the tree.

### BDD_reduce
First, the program takes all the leaves according to the maximum depth of the corresponding diagram (tree) and reduces them so that there is always only one node for `1` and one node for `0`. This is achieved by selecting the first found `1` and `0` as a pattern and redirecting all others to them. This step alone reduces the tree by approximately 50%, as the layer with the leaves is the largest and makes up about half of the diagram.

Further reduction proceeds from bottom to top (from the leaves to the root), which is less efficient than reducing from the root, but since it is necessary to count how many nodes are removed, it is necessary to reduce step by step.
Thus, the program gradually goes from the deepest layers to the root. In each layer, all corresponding nodes are checked and compared with each other. If a duplicate is found, references to the children are changed using the parent reference so that one of the duplicate nodes is eliminated, and Java's garbage collection takes care of the rest by removing the unused reference.

### BDD_use
This method traverses the diagram (tree) very simply based on the value of the corresponding variables. If the variable is `0`, it goes left; if the variable is `1`, it goes right. All variables are evaluated this way, but for correct evaluation, the depth must always match the order of the input variables to ensure the correct variable is being evaluated.


# SK
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

## Stručný opis algoritmu
### BDD_creat
Samotné vytváranie stromu prebieha v pomocnej rekurzívnej metóde, kt. je zložená z dvoch if podmienok. Prvá if podmienka slúži pre ľavého potomka a funguje tak, že sa vytvorí nový string, kt. bude obsahovať ľavú polovicu stringu rodiča, nastaví sa pointer na rodiča a ďalej nasleduje rekurzívne volanie. Rovnako funguje aj druhý if pre pravého potomka. Funkcia do každého nodu nastaví aj jeho príslušnú hĺbku a nastaví sa aj maximálna hĺbka pre daný strom. <br>

### BDD_reduce
Ako prvé program podľa maximálnej hĺbky príslušného diagramu (stromu) vezme všetky listy a zredukuje ich tak aby sme mali vždy iba jeden node pre 1 a jeden node pre 0. To sa dosiahne tak, že prvá nájdená 1 a 0 sa zvolí ako vzor a všetky ostatné sa nato iba presmerujú. Už tento krok zabezpečí zredukovanie stromu o cca 50% pretože vrstva s listami je najväčšia a tvorí približne polovicu diagramu. <br>

Ďalšie redukovanie prebieha smerom zdola hore (od listov ku koreňu) čo je menej efektívne ako keby redukujeme smerom od koreňa avšak nakoľko je potrebné počítať koľko nodes sa odstráni je potrebné redukovať postupne po jednom. <br>
Následne sa teda postupne prechádza od najhlbších vrstiev až po koreň. V každej vrstve sú skontrolované všetky príslušné nodes a porovnané každý s každým. Ak sa nájde duplikát tak sa pomocou referencie na rodiča zmenia referencie na potomkov tak aby jeden z dvojice duplikátov zanikol a tým, že zanikne referencia na duplikát sa už Java garbage collection postará o zvyšok. <br>

### BDD_use
Veľmi jednoducho prechádza diagramom (stromom) podľa hodnoty príslušných premenných. Ak je premenná 0 ide sa vľavo ak je premenná 1 ide sa vpravo. Takto sa postupne vyhodnocujú všetky premenné avšak nato aby nastalo vyhodnotenie musí vždy hĺbka súhlasiť s poradím premennej na vstupe aby bolo zabezpečené, že sa vyhodnocuje správna premenná.
