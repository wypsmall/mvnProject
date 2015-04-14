logsouce启动命令
java -Dfile.encoding=UTF-8 -jar logsource.jar >/dev/null 2>&1 &

flume spool启动
./flume-ng agent -c ../conf/ -f ../conf/spool.conf -n a1 &
./flume-ng agent -c ../conf/ -f ../conf/kafka-flume.conf -n a1 &

./flume-ng agent -c ../conf/ -f ../conf/kafka-flume.conf -n a1 -Dflume.root.logger=INFO,console
./flume-ng agent -c ../conf/ -f ../conf/spool.conf -n a1 -Dflume.root.logger=INFO,console

./kafka-server-start.sh ../config/server.properties >/dev/null 2>&1 &

java -Dfile.encoding=UTF-8 -jar logsource.jar 10 30 >/dev/null 2>&1 &

./storm jar logstorm.jar com.neo.StormTopology logstorm 2 2 1 5
./storm kill logstorm