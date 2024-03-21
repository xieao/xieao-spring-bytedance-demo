#!/bin/sh

chmod +x ./mvnw
exec ./mvnw -T6 -DskipTests clean package
