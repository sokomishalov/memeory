# Memeory
[![MIT license](https://img.shields.io/badge/license-MIT-purple.svg)](https://opensource.org/licenses/MIT)
[![codebeat badge](https://codebeat.co/badges/1232b163-6bdf-4f75-951f-9252af33cdaf)](https://codebeat.co/projects/github-com-sokomishalov-memeory-master)

This repository contains:
 - [backend](./backend) (Kotlin + Spring boot) 
 - [mobile application](./mobile-app) (Flutter)
 - [web application](./web-app) (React JS) 

This is just another pet project. There is no rocket science inside.

### Backend

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

After fetching and parsing memes are ready to be stored in db. 
MongoDB is current storage of all information so far.

### Mobile app

Mobile app so far allows to:
 - authorize with either google or facebook for sharing user 
 customizations between devices
 - switch between light and dark themes
 - choose memes' scrolling axis
 - choose channels
 - laugh out loud every day :)

### Web app

Web app so far allows to:
 - laugh out loud every day :)