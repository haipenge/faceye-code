echo '############################Build Script#####################'
echo '##'
echo '#############################################################'
ROOT=$(dirname $(cd "$(dirname "$0")";pwd))
echo '>>>Start to install faceye-code-entity...'
echo 'ROOT Dir is:' + $ROOT
mvn clean compile package install -D maven.test.skip=true -P product
exit 0 
