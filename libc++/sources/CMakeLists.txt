set(LIB_SOURCES {{PROJECT_NAME}}.cpp)

# Create the library
if(BUILD_SHARED_LIBS)
  add_library($\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME} SHARED ${LIB_SOURCES})
else()
  add_library($\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME} STATIC ${LIB_SOURCES})
endif()

# Set include directories for the library
target_include_directories(
  $\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME} PRIVATE $<BUILD_INTERFACE:$\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/include>)

# target_link
#target_link_libraries($\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME} PUBLIC rcli)

# set properties
set_target_properties(
  $\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}
  PROPERTIES ARCHIVE_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/lib"
             LIBRARY_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/lib"
             SOVERSION $\{{{PROJECT_NAME_UPPERCASE}}_LIB_VERSION_MAJOR}
             VERSION $\{{{PROJECT_NAME_UPPERCASE}}_LIB_VERSION})

# lib install configuration
if({{PROJECT_NAME_UPPERCASE}}_LIB_INSTALL)
  include(CMakePackageConfigHelpers)
  write_basic_package_version_file(
    ${CMAKE_BINARY_DIR}/cmake/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Version.cmake
    VERSION $\{{{PROJECT_NAME_UPPERCASE}}_VERSION}
    COMPATIBILITY SameMajorVersion)

  include(GNUInstallDirs)
  install(DIRECTORY $\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/include/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}
          DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})

  install(
    TARGETS $\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}
    EXPORT $\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Targets
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR} COMPONENT Runtime
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR} COMPONENT Development
    ARCHIVE DESTINATION ${CMAKE_INSTALL_LIBDIR} COMPONENT Development)

  # Install the Config and ConfigVersion files
  install(
    EXPORT $\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Targets
    FILE $\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Targets.cmake
    DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME})

  configure_file($\{{{PROJECT_NAME_UPPERCASE}}_SOURCE_DIR}/cmake/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Config.cmake.in
                 ${CMAKE_BINARY_DIR}/cmake/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Config.cmake @ONLY)
  # Install the Config, ConfigVersion, and custom Find module files
  install(FILES ${CMAKE_BINARY_DIR}/cmake/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Config.cmake
                ${CMAKE_BINARY_DIR}/cmake/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME}Version.cmake
          DESTINATION ${CMAKE_INSTALL_LIBDIR}/cmake/$\{{{PROJECT_NAME_UPPERCASE}}_LIB_NAME})
endif()
