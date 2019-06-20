import 'package:flutter/material.dart';

class PhotoAttachment extends StatelessWidget {
  const PhotoAttachment({Key key, this.url}) : super(key: key);

  final String url;

  @override
  Widget build(BuildContext context) {
    return Container(
//      child: CachedNetworkImage(
//        imageUrl: url,
//        placeholder: (context, url) => Loader(),
//        errorWidget: (context, url, error) => new Icon(Icons.error),
//      ),
      child: Image.network(url),
    );
  }
}
