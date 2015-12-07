#!/bin/bash
JAVAC=javac
DEST=bin
$JAVAC src/Main.java -sourcepath src -cp lib/colt.jar -d $DEST
rm -rf $DEST/resources
rm -rf $DEST/lib
svn export src/resources $DEST/resources
svn export lib/ $DEST/lib
cd bin
jar cfm ../jar/SUMOTrafficModeler.jar ../MANIFEST.MF .
