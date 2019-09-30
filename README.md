# Memeory
[![Apache License 2](https://img.shields.io/badge/license-ASF2-purple.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)

This repository contains:
 - [backend](./backend) (Kotlin + Spring boot) 
 - [mobile application](./mobile-app) (Flutter) 

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

Mobile app allows to:
 - authorize with either google or facebook for sharing user 
 customizations between devices
 - switch between light and dark themes
 - choose memes' scrolling axis
 - choose channels
 - laugh out loud every day :)