cmake_minimum_required(VERSION 3.18)

project({{PROJECT_NAME}}_tools VERSION 0.0.1)

# c++ 17 by default
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

# deps
#find_package(rcli REQUIRED)

set({{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR ${CMAKE_CURRENT_SOURCE_DIR})
set({{PROJECT_NAME_UPPERCASE}}_IS_MAIN_PROJECT OFF)
if(CMAKE_CURRENT_SOURCE_DIR STREQUAL CMAKE_SOURCE_DIR)
  set({{PROJECT_NAME_UPPERCASE}}_IS_MAIN_PROJECT ON)
endif()

# target names
set({{PROJECT_NAME_UPPERCASE}}_LIB_NAME {{PROJECT_NAME}})
set({{PROJECT_NAME_UPPERCASE}}_LIB_VERSION 0.0.1)
set({{PROJECT_NAME_UPPERCASE}}_LIB_VERSION_MAJOR 0)
set({{PROJECT_NAME_UPPERCASE}}_VERSION ${PROJECT_VERSION})
set({{PROJECT_NAME_UPPERCASE}}_VERSION_MAJOR ${PROJECT_VERSION_MAJOR})

# list of options
option(BUILD_SHARED_LIBS "Build rcli as a shared library"
       $\{{{PROJECT_NAME_UPPERCASE}}_IS_MAIN_PROJECT})
option({{PROJECT_NAME_UPPERCASE}}_LIB_BUILD "Build {{PROJECT_NAME}} lib" ON)
option({{PROJECT_NAME_UPPERCASE}}_LIB_INSTALL "Install {{PROJECT_NAME}} lib" $\{{{PROJECT_NAME_UPPERCASE}}_IS_MAIN_PROJECT})

# configure build type
if(NOT CMAKE_BUILD_TYPE)
  set(CMAKE_BUILD_TYPE "Debug")
endif()

if(WIN32)
  add_compile_options(WIN32)
endif()

if(CMAKE_BUILD_TYPE STREQUAL "Release")
  set({{PROJECT_NAME_UPPERCASE}}_TEST OFF)
endif()

if({{PROJECT_NAME_UPPERCASE}}_IS_MAIN_PROJECT)
  configure_file($\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/.templates/PKGBUILD.in
                 $\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/PKGBUILD @ONLY)

  configure_file($\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/.templates/create-release.sh.in
                 $\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/create-release.sh @ONLY)

  configure_file(
    $\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/.templates/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Config.hpp.in
    $\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/include/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Config.hpp
    @ONLY)
endif()

add_subdirectory(sources)

# clang-format configuration
find_program(CLANG_FORMAT_EXECUTABLE NAMES clang-format)
if(CLANG_FORMAT_EXECUTABLE)
  file(GLOB_RECURSE ALL_SOURCE_FILES "$\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/sources/*.cpp"
       "$\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/include/{{PROJECT_NAME}}/*.hpp")
  add_custom_target(
    clang-format
    COMMAND ${CLANG_FORMAT_EXECUTABLE} -i -style=file ${ALL_SOURCE_FILES}
    COMMENT "Running clang-format")
else()
  message(
    WARNING "clang-format not found. Clang-format target will not be available."
  )
endif()

# CPack configuration
set(CPACK_PACKAGE_VENDOR "{{AUTHOR}} <{{AUTHOR_EMAIL}}>")
set(CPACK_PACKAGE_DESCRIPTION_SUMMARY
    {{DESCRIPTION}}
)
include(CPack)
