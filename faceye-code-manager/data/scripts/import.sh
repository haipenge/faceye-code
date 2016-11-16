#/bin/bash
mongo_bin=/app/mongo/mongodb-linux-x86_64-3.0.1/bin
mongo_import_dir=/tmp
echo '> start to import clazz ....'
sh $mongo_bin/mongoimport -d search -c code_clazz_clazz --batchSize=100 $mongo_import_dir/code_clazz_clazz
echo '> finish import clazz ....'
echo '> start to import pkg...'
sh $mongo_bin/mongoimport -d search -c code_clazz_pkg --batchSize=100 $mongo_import_dir/code_clazz_pkg
echo '> finish import pkg'
exit 0