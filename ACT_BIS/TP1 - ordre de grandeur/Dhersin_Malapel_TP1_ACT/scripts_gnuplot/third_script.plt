# echelle
set yrange[0:140000]
set xrange[0:5000]
# question 2.1.5
#plot "alea_tableau_1.txt" using 1:2 title "Courbe Min" with lines, "alea_tableau_1.txt" using 1:3 title "Courbe Moyenne" with lines, "alea_tableau_1.txt" using 1:4 title "Courbe Max" with lines
# question 2.2.2
plot "alea_tableau_1.txt" using 1:2 title "Min mesureMinSimple" with lines, "alea_tableau_1.txt" using 1:3 title "Moyenne mesureMinSimple" with lines, "alea_tableau_1.txt" using 1:4 title "Max mesureMinSimple" with lines, "alea_tableau_1.txt" using 1:5 title "Min mesureMinTri" with lines, "alea_tableau_1.txt" using 1:6 title "Moyenne mesureMinTri" with lines, "alea_tableau_1.txt" using 1:7 title "Max mesureMinTri" with lines
pause -1



