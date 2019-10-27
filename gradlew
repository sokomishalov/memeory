#!/usr/bin/env sh
cp -r ./additional/devops/heroku/backend/Procfile ./ && \
cp -r ./additional/gradle ./backend/ && \
cp -r ./additional/gradle/gradlew ./backend/ && \
cd backend && \
./gradlew build