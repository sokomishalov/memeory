import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:memeory/components/containers/error.dart';
import 'package:memeory/components/containers/loader.dart';

class ImageAttachment extends StatelessWidget {
  const ImageAttachment({Key key, this.url, this.aspectRatio})
      : super(key: key);

  final String url;
  final double aspectRatio;

  @override
  Widget build(BuildContext context) {
    var width = MediaQuery.of(context).size.width;
    var height = aspectRatio != null
        ? MediaQuery.of(context).size.width / aspectRatio
        : null;

    return Container(
      child: CachedNetworkImage(
        width: width,
        height: height,
        imageUrl: url,
        placeholder: (context, url) => Loader(
          width: width,
          height: height,
        ),
        errorWidget: (context, url, error) => ErrorContainer(error: error),
      ),
//      child: Image(
//        image: NetworkImage(url),
//        width: width,
//        height: height,
//      ),
    );
  }
}
