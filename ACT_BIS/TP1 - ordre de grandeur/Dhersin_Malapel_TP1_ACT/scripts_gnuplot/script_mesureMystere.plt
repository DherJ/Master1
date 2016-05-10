# echelle
set yrange[0:2000000000]
set xrange[0:1500]

# question 2.3.1 - mesureMystere
plot "mesureMystere.txt" using 1:2 title "Min mesureMystere-tab" with lines, "mesureMystere.txt" using 1:3 title "Moyenne mesureMystere-tab" with lines, "mesureMystere.txt" using 1:4 title "Max mesureMystere-tab" with lines, "mesureMystere.txt" using 1:5 title "Min mesureMystere-list" with lines, "mesureMystere.txt" using 1:6 title "Moyenne mesureMystere-list" with lines, "mesureMystere.txt" using 1:7 title "Max mesureMystere-list" with lines
pause -1
