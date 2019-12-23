import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';

class NetworkLogo extends StatelessWidget {
  const NetworkLogo({
    Key key,
    @required this.url,
    this.size = 30,
  }) : super(key: key);

  final String url;
  final double size;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: size,
      height: size,
      decoration: url.isEmpty
          ? BoxDecoration(
              color: Colors.transparent,
            )
          : BoxDecoration(
              borderRadius: BorderRadius.circular(10),
              image: DecorationImage(
                image: CachedNetworkImageProvider(
                  url,
                ),
              ),
            ),
    );
  }
}
