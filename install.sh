ROOT=$(dirname $(cd "$(dirname "$0")";pwd))
cd $ROOT/faceye-code-entity
sh install.sh
cd $ROOT/faceye-code-manager
sh install.sh
cd $ROOT
exit 0
