import 'package:flutter/material.dart';

class PhotoAttachment extends StatelessWidget {
  const PhotoAttachment({Key key, this.url, this.aspectRatio})
      : super(key: key);

  final String url;
  final double aspectRatio;

  @override
  Widget build(BuildContext context) {
    return Container(
//      // FIXME CachedNetworkImage works much slower :(
//      child: CachedNetworkImage(
//        width: MediaQuery.of(context).size.width,
//        height: aspectRatio != null
//            ? MediaQuery.of(context).size.width / aspectRatio
//            : null,
//        imageUrl: url,
//        placeholder: (context, url) => Loader(),
//        errorWidget: (context, url, error) => ErrorContainer(error: error),
//      ),
      child: Image(
        image: NetworkImage(url),
        width: MediaQuery.of(context).size.width,
        height: aspectRatio != null
            ? MediaQuery.of(context).size.width / aspectRatio
            : null,
      ),
    );
  }
}
