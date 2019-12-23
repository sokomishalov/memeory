import 'package:flutter/material.dart';
import 'package:memeory/api/channels.dart';
import 'package:memeory/components/images/network_logo.dart';

class ChannelLogo extends StatelessWidget {
  const ChannelLogo({Key key, this.channelId}) : super(key: key);

  final String channelId;

  @override
  Widget build(BuildContext context) {
    return NetworkLogo(
      url: getChannelLogoUrl(channelId),
      size: 35,
    );
  }
}
