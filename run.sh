a=y
echo Where is the main class?
read f
while [ -r $f.java -a $a == "y" ]
do
	if [ -r $f.java ]; then 
		javac *.java
		java $f
	fi
	echo Would you run that again?[y/n]
	read a
done
