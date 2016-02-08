# Args
FLAG=$1

# Constants
ON="on"
OFF="off"

# Prepare the working directory
rm -r src/test/*
cp -r koan-templates/* src/test

# Process files
if [ "$FLAG" = "$OFF" ]; then
  find src/test -type f -print0 | xargs -0 sed -E -i '' -e "s/(_{2,})\`(.*)\`/\1/g"
else
  find src/test -type f -print0 | xargs -0 sed -E -i '' -e "s/(_{2,})\`(.*)\`/\2/g"
fi