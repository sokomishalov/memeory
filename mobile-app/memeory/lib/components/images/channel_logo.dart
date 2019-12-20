import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:memeory/api/channels.dart';

class ChannelLogo extends StatefulWidget {
  const ChannelLogo({Key key, this.channelId}) : super(key: key);

  final String channelId;

  @override
  _ChannelLogoState createState() => _ChannelLogoState();
}

class _ChannelLogoState extends State<ChannelLogo> {
  String _url = "";

  @override
  void initState() {
    super.initState();
    getChannelLogoUrl(widget.channelId).then((url) {
      setState(() {
        _url = url;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 35,
      height: 35,
      decoration: _url.isEmpty
          ? BoxDecoration(
              color: Colors.transparent,
            )
          : BoxDecoration(
              borderRadius: BorderRadius.circular(10),
              image: DecorationImage(
                image: CachedNetworkImageProvider(
                  _url,
                ),
              ),
            ),
    );
  }
}
