#!/usr/bin/env bash
./gradlew InspectorLibrary:uploadArchives -PnexusUsername="${SONATYPE_USERNAME}" -PnexusPassword="${SONATYPE_PASSWORD}"