#/bin/bash
mongo_bin=/data/tools/mongo/mongodb-linux-x86_64-3.0.7/bin
mongo_export_dir=/data/bak/mongo
sh $mongo_bin/mongoexport -h 127.0.0.1 -d search -c code_clazz_clazz -q '{times:{$gt:100}}' -o $mongo_export_dir/code_clazz_clazz
sh $mongo_bin/mongoexport -h 127.0.0.1 -d search -c code_clazz_pkg -o $mongo_export_dir/code_clazz_pkg