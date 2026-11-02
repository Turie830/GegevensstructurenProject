# GegevensstructurenProject
Code voor het project van Gegevensstructuren en algoritmen X0A45A 2026-2027. De gekozen heuristieken zijn MED1 en SP, en de gekozen metaheuristiek is SA.




# MED1

Verloop van 1 ordeningsstap:

- Zet de rechterlaag vast.
- Bekijk voor elke linkerknoop op welke posities zijn buren rechts staan.
- Sorteer die buurposities en bepaal de mediaan.
- Orden daarna de linkerknopen volgens oplopende mediaan.

Voorbeeld:

| Knoop | Buurposities | Mediaan |
|-------|--------------|---------|
| A     | 1, 2, 7      | 2       |
| B     | 3, 5, 6      | 5       |
| C     | 2, 3, 4      | 3       |

→ De nieuwe volgorde is: **A C B**

Na het ordenen van links kun je links vasthouden en rechts ordenen.

**Bij gelijke medianen: We kiezen hier om de volgorde niet aan te passen.
**Bij een even aantal buurposities: Bereken het gemiddelde tussen de 2 middelste buurposities.


```mermaid
flowchart TD
    A([Start MED1]) --> B["Ontvang knoopvolgorde, burenlijsten<br/>en posities van de andere laag"]
    B --> C["Bewaar de oude positie van elke knoop"]
    C --> D["Neem de volgende knoop u"]
    D --> E["Verzamel de huidige posities<br/>van alle buren van u"]
    E --> F{"Heeft u buren?"}

    F -- Nee --> G["Gebruik de oude positie<br/>als sorteersleutel"]
    F -- Ja --> H{"Aantal buren even?"}

    H -- Nee --> I["Vind de middelste waarde<br/>met Quickselect"]
    H -- Ja --> J["Vind beide middelste waarden<br/>met Quickselect en neem hun gemiddelde"]

    I --> K["Bewaar de sorteersleutel voor u"]
    J --> K
    G --> K

    K --> L{"Nog knopen te verwerken?"}
    L -- Ja --> D
    L -- Nee --> M["Sorteer de knopen op hun medianen<br/>met stabiele mergesort"]
    M --> N["Werk het positie-array bij"]
    N --> O([Geef nieuwe volgorde en posities terug])

    classDef endpoint fill:#19344d,color:#fff,stroke:#19344d
    classDef process fill:#e9f2ff,stroke:#699bea,color:#19344d
    classDef decision fill:#fff4d8,stroke:#e5ad38,color:#785000
    classDef result fill:#e2f4eb,stroke:#65b98e,color:#206543

    class A,O endpoint
    class B,C,D,E,G,I,J,K process
    class F,H,L decision
    class M,N result
```



# Splitting – SP

Kernidee: kies een spil en verdeel de andere knopen volgens hun kruisingskosten.

K(i, j) = aantal kruisingen tussen verbindingen van linkerknopen i en j, wanneer i voor j staat.

In het algemeen: K(i, j) ≠ K(j, i).

## Splittingprincipe

Kies in de te ordenen groep een spil u. Voor elke andere knoop v vergelijk je de 2 mogelijkheden:

- v voor u: K(v, u)
- v na u: K(u, v)

Plaats v aan de kant met de kleinste bijdrage. Orden daarna de groep voor u en de groep na u elk opnieuw met hetzelfde splittingprincipe.

Voorbeeld:

| Knoop v | K(v, u) | K(u, v) | Beslissing |
|---------|---------|---------|------------|
| A       | 0       | 2       | A voor u   |
| B       | 2       | 0       | B na u     |
| C       | 1       | 1       | Gelijke kosten: laat de knoop staan waar hij oorspronkelijk stond. |



```mermaid
flowchart TD
    A([Start Splitting]) --> B["Ontvang een groep knopen,<br/>burenlijsten en posities van de andere laag"]
    B --> C{"Hoogstens één knoop?"}

    C -- Ja --> D([Geef de groep terug])
    C -- Nee --> E["Kies de middelste knoop als spil"]
    E --> F["Maak twee lege lijsten:<br/>voorSpil en naSpil"]
    F --> G["Neem de volgende knoop v,<br/>behalve de spil"]

    G --> H["Bereken rechtstreeks via de buren:<br/>kostVoor = kruisingen bij v vóór spil<br/>kostNa = kruisingen bij spil vóór v"]
    H --> I{"Vergelijk de kosten"}

    I -- kostVoor kleiner --> J["Voeg v toe aan voorSpil"]
    I -- kostNa kleiner --> K["Voeg v toe aan naSpil"]
    I -- Gelijk --> L["Voeg v toe aan de kant waar hij<br/>oorspronkelijk tegenover de spil stond"]

    J --> M{"Nog knopen te verwerken?"}
    K --> M
    L --> M

    M -- Ja --> G
    M -- Nee --> N["Orden voorSpil recursief<br/>met Splitting"]
    N --> O["Orden naSpil recursief<br/>met Splitting"]
    O --> P["Voeg samen:<br/>geordendVoor + spil + geordendNa"]
    P --> Q([Geef de geordende groep terug])

    classDef endpoint fill:#19344d,color:#fff,stroke:#19344d
    classDef process fill:#e9f2ff,stroke:#699bea,color:#19344d
    classDef decision fill:#fff4d8,stroke:#e5ad38,color:#785000
    classDef recursive fill:#efe7fb,stroke:#a77be8,color:#563584
    classDef result fill:#e2f4eb,stroke:#65b98e,color:#206543

    class A,D,Q endpoint
    class B,E,F,G,H,J,K,L process
    class C,I,M decision
    class N,O recursive
    class P result
```



# Simulated Annealing – SA

Kernidee: accepteer soms een slechtere oplossing; verminder die kans tijdens het zoeken.

>Hoe kies je de random aanpassing: Kies willekeurig de linker of rechterlaag en kies daarin willekeurig 2 knopen en verwissel hun positie. Accepteer of verwerp volgens SA-regels.

Een lokale zoekmethode die alleen verbeteringen accepteert, kan vastlopen. SA laat soms een stap met meer kruisingen toe.

- s = huidige oplossing
- s′ = kandidaat
- Δ = C(s′) − C(s) (kostenverschil)

Acceptatieregel:

- Δ ≤ 0: accepteer de kandidaat.
- Δ > 0: accepteer de kandidaat met kans e^(−Δ/T), met T > 0.

## Voorbeeld

De huidige oplossing heeft 20 kruisingen. De kandidaat heeft er 23.

Δ = 3. Bij T = 1,0 is de acceptatiekans ongeveer 0,050.

Trek je een uniform willekeurig getal tussen 0 en 1, dan accepteer je wanneer dat kleiner is dan die kans.

Een kandidaat met 18 kruisingen wordt altijd geaccepteerd.

## Afkoelschema

T daalt volgens een afkoelschema, bijvoorbeeld:

T_nieuw = α · T, met 0 < α < 1.

## Huidige en beste oplossing

De huidige oplossing en de beste oplossing zijn verschillend. De huidige oplossing kan verslechteren.

Bewaar daarnaast de beste oplossing die tijdens het zoeken gevonden is. Die is het uiteindelijke resultaat.


