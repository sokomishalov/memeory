import 'package:flutter/material.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/util/strings/strings.dart';

class MemeoryAppBar extends StatelessWidget {
  const MemeoryAppBar({Key key, this.screenArgs}) : super(key: key);

  final MemesScreenArgs screenArgs;

  @override
  Widget build(BuildContext context) {
    var caption;
    if (screenArgs.providerId.isNotEmpty) {
      caption = "Provider: ${screenArgs.providerId.capitalize()}";
    } else if (screenArgs.topicId.isNotEmpty) {
      caption = "Topic: ${screenArgs.topicId}";
    } else if (screenArgs.channelId.isNotEmpty) {
      caption = "Channel: ${screenArgs.topicId}";
    } else if (screenArgs.memeId.isNotEmpty) {
      caption = "Meme: ${screenArgs.memeId}";
    } else {
      caption = "All memes";
    }

    return AppBar(
      title: Text(caption),
    );
  }
}
