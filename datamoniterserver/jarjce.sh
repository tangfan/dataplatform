cd ./classes
jarname=BookBase_DataMoniterServer_DataMoniterServant.jar
jarpath=./com/qq/bookbase/datamoniterserver/datamoniterservant/
jarfiles=`find $jarpath -iname *.class | xargs -n 1 -i echo -n {}' '`
/usr/local/jdk/bin/jar cvf $jarname $jarfiles
echo "-- $jarname "
jcelibpath=/home/tafjce/BookBase/DataMoniterServer
if [[ ! -d $jcelibpath ]]
then
	mkdir -p $jcelibpath
fi
cp ./$jarname $jcelibpath/
rm -f ./$jarname
cp ../src/*.jce $jcelibpath/
echo "-- $jcelibpath/$jarname"


