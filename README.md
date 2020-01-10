<a href="https://sokomishalov.github.io/memeory/">
    <img src="./additional/logo/logo.png" width="200" height="200"/>
</a>

# Memeory
[![MIT license](https://img.shields.io/badge/license-MIT-purple.svg)](https://opensource.org/licenses/MIT)
[![codebeat badge](https://codebeat.co/badges/1232b163-6bdf-4f75-951f-9252af33cdaf)](https://codebeat.co/projects/github-com-sokomishalov-memeory-master)

This repository contains:
 - [backend](./backend) (Kotlin + Spring boot) 
 - [mobile application](./mobile-app/memeory) (Flutter)
 - [web application](./web-app) (React) 
 - [telegram bot](./backend/telegram) (Kotlin + Spring boot)

This is just another pet project. There is no rocket science inside.

### Backend side

There is the scheduler which is requesting for a batch of memes
from different sources. Most of meme providers are scrapers.
The list of implemented meme sources looks like this so far:
- [reddit](https://www.reddit.com)
- [facebook](https://www.facebook.com)
- [instagram](https://www.instagram.com)
- [twitter](https://twitter.com)
- [9gag](https://9gag.com)
- [pinterest](https://www.pinterest.com)
- [vk](https://vk.com)
- [ifunny](https://ifunny.co)

After fetching and parsing memes are ready to be stored in the database. 
MongoDB is current storage of all information so far.

### Client side

Clients allow to:
 - switch between light and dark themes `(mobile)`
 - choose memes' scrolling axis `(mobile)`
 - receive relevant memes every day `(bot)`
 - laugh out loud every day :) `(mobile/web/bot)`