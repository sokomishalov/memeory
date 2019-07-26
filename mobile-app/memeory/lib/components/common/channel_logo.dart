import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:memeory/util/env.dart';

class ChannelLogo extends StatelessWidget {
  const ChannelLogo({Key key, this.channelId}) : super(key: key);

  final String channelId;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 35,
      height: 35,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
        image: DecorationImage(
          image: CachedNetworkImageProvider(
            '${env.backendUrl}/channels/logo/$channelId',
          ),
        ),
      ),
    );
  }
}
