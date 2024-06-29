#!/bin/bash
if [ "$#" -lt 1 ]; then
    exit 1
fi

RELEASE_TO_CREATE="$1"

SHA_PGKBUILD_LINE=12
release_path=()

cmake -DCMAKE_BUILD_TYPE=Release -S . -B build
cd build 
make
cd ..

mkdir -p release
cd release

create_tar_release(){
    local RELEASE_NAME="$1"
    local PKGBUILD_PATH="$2"

    # create tar release and configure hash
    tar -czvf "${RELEASE_NAME}.tar.gz" "${RELEASE_NAME}"
    hash_value=$(sha256sum "${RELEASE_NAME}.tar.gz" | awk '{print $1}')
    new_shaline="sha256sums=(\"${hash_value}\")"
    sed -i "${SHA_PGKBUILD_LINE}s/.*/${new_shaline}/" "${PKGBUILD_PATH}" 
}

create_lib_release(){

    local RELEASE_NAME={{PROJECT_NAME_LOWERCASE}}@@{{PROJECT_NAME_UPPERCASE}}_LIB_VERSION@
    release_path+=("${RELEASE_NAME}")
    
    mkdir -p "${RELEASE_NAME}"
    mkdir -p "${RELEASE_NAME}/lib"
    mkdir -p "${RELEASE_NAME}/include"
    mkdir -p "${RELEASE_NAME}/lib/cmake"
    
    cp -r ../build/lib "${RELEASE_NAME}"
    cp -r ../build/cmake "${RELEASE_NAME}/lib/cmake/{{PROJECT_NAME}}"
    cp -r ../include/{{PROJECT_NAME}} "${RELEASE_NAME}/include/{{PROJECT_NAME}}"

    create_tar_release "${RELEASE_NAME}" "../PKGBUILD/lib/PKGBUILD"
}

create_lib_release

git add --all
git commit -m "release: {{PROJECT_NAME_LOWERCASE}}@@{{PROJECT_NAME_UPPERCASE}}_LIB_VERSION@"
git tag -a v@{{PROJECT_NAME_UPPERCASE}}_VERSION@ -m "{{PROJECT_NAME_LOWERCASE}} v@{{PROJECT_NAME_UPPERCASE}}_VERSION@"