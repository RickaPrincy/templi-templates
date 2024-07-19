#! /bin/bash
git clone -b v0.0.1 {{GIT_URL}} 
cd {{PROJECT_NAME}} 
mkdir build
cd build
cmake -DCMAKE_BUILD_TYPE=Release -S .. -B .
sudo make install
cd ../..
rm -rf {{PROJECT_NAME}} 
