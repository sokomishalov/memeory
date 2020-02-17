<a href="https://sokomishalov.github.io/memeory/">
    <img src="./additional/logo/logo.png" width="200" height="200"/>
</a>

# Memeory
[![MIT license](https://img.shields.io/badge/license-MIT-purple.svg)](https://opensource.org/licenses/MIT)

This repository contains:
 - [backend](./backend) (Kotlin + Spring boot) 
 - [mobile application](./mobile-app/memeory) (Flutter)
 - [web application](./web-app) (ReactJS) 
 - [telegram bot](./backend/telegram) (Kotlin + Spring boot)

This is just another pet project. There is no rocket science inside.

### Backend side

There is the scheduler which is requesting for a batch of memes
from different sources. Most meme providers are scrapers. 
I use my custom library for that purposes - [skraper](https://github.com/SokoMishaLov/skraper)

After fetching and parsing memes are ready to be stored in the database. 
MongoDB is current storage of all information so far.

### Client side

Clients allow to:
 - switch between light and dark themes `(mobile)`
 - choose memes' scrolling axis `(mobile)`
 - receive relevant memes every day `(bot)`
 - laugh out loud every day :) `(mobile/web/bot)`