# GameOfLife

GameOfLife is a Java application which models the survival system of sexual and asexual cells.

The system is modeled by the following rules:
 - there is a limited number of food units which cells will consume. 
 - after consuming a unit, a cell will not be hungry again for a time "t_full".
 - if a hungry cell cannot eat anything for a time "t_starve" it will die and a random number (between 1 and 5) of food units will spawn
 - after a cell ate for at least 10 times a cell will reproduce before it will become hungry again. There are two types of cells which reproduce differently
 - asexual cells will reproduce by splitting into two cells, both hungry
 - sexual cells will need to find another sexual cell which ate at least 10 times. If a pair of sexual cells reproduces, a third hungry cell will spawn and the parent cells will become hungry again.
 
The purpose of this simulation is to explore Java's multithreading capabilities.
