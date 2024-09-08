#!/bin/bash

# Variables
TARGET_DIR=$1
OLD_NAME=$2
NEW_NAME=$3

# rename folders
find "${TARGET_DIR}" -type d -name "*${OLD_NAME}*" | while read -r dir; do
  new_dir=$(echo "${dir}" | sed "s/${OLD_NAME}/${NEW_NAME}/g")
  mv "${dir}" "${new_dir}"
done
