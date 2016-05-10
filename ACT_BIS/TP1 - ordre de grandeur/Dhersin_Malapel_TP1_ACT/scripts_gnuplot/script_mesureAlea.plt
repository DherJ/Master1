# echelle
set yrange[0:200000000]
set xrange[0:500000]

# question 2.3.1 - mesureAlea
plot "mesuresAlea.txt" using 1:2 title "Min mesureAlea-tab" with lines, "mesuresAlea.txt" using 1:3 title "Moyenne mesureAlea-tab" with lines, "mesuresAlea.txt" using 1:4 title "Max mesureAlea-tab" with lines, "mesuresAlea.txt" using 1:5 title "Min mesureAlea-list" with lines, "mesuresAlea.txt" using 1:6 title "Moyenne mesureAlea-list" with lines, "mesuresAlea.txt" using 1:7 title "Max mesureAlea-list" with lines
pause -1