#------------------ MAKEFILE TP-Problemes-NP ------------------#
#  			Dhersin Jerome                         #
# 			Knapik Christopher                     #
#  			Master 1 informatique                  #
#  			02/12/2014                             #
#--------------------------------------------------------------#

JFLAGS = -g
JC = javac
JVM = java
JDOC = javadoc
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
SRC = \
	Solve.java \
	PblBinPack.java \
	PblDec.java \
	PblPartition.java \
	Certificat.java \
	CertificatBinPack.java 

CLASSES = \
	Solve.class \
	PblBinPack.class \
	PblDec.class \
	PblPartition.class \
	Certificat.class \
	CertificatBinPack.class 
	
SOLVE = Solve

default: classes doc

classes: $(CLASSES:.java=.class)

run: $(SOLVE).class
	$(JVM) $(CLASSES)

# generer la javadoc
doc: $(CLASSES)
	mkdir -p ../doc
	$(JDOC) $(JDOCFLAGS) -d ../doc $(SRC)
	
clear:
	$(RM) $(CLASSES)
	$(RM) -f ../doc/*
	$(RM) -f ../doc/ressources/*
	$(RM) -d ../doc/*
