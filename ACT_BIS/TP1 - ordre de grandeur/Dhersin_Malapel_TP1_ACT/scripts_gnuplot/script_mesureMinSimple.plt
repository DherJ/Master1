# echelle
set yrange[0:200000000]
set xrange[0:500000]

# question 2.3.1 - mesureMinSimple
plot "mesureSimple.txt" using 1:2 title "Min mesureMinSimple-tab" with lines, "mesureSimple.txt" using 1:3 title "Moyenne mesureMinSimple-tab" with lines, "mesureSimple.txt" using 1:4 title "Max mesureMinSimple-tab" with lines, "mesureSimple.txt" using 1:5 title "Min mesureMinSimple-list" with lines, "mesureSimple.txt" using 1:6 title "Moyenne mesureMinSimple-list" with lines, "mesureSimple.txt" using 1:7 title "Max mesureMinSimple-list" with lines
pause -1