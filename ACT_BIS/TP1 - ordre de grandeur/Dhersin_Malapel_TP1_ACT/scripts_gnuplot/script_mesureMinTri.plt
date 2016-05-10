# echelle
set yrange[0:200000000]
set xrange[0:500000]

# question 2.3.1 - mesureMinTri
plot "mesureTri.txt" using 1:2 title "Min mesureMinTri-tab" with lines, "mesureTri.txt" using 1:3 title "Moyenne mesureMinTri-tab" with lines, "mesureTri.txt" using 1:4 title "Max mesureMinTri-tab" with lines, "mesureTri.txt" using 1:5 title "Min mesureMinTri-list" with lines, "mesureTri.txt" using 1:6 title "Moyenne mesureMinTri-list" with lines, "mesureTri.txt" using 1:7 title "Max mesureMinTri-list" with lines
pause -1