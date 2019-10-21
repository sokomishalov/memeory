import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:memeory/util/os/os.dart';

class Loader extends StatelessWidget {
  const Loader({Key key, this.width, this.height}) : super(key: key);

  final double width;
  final double height;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: width,
      height: height,
      child: Center(
        child: isIos()
            ? const CupertinoActivityIndicator()
            : const CircularProgressIndicator(strokeWidth: 2),
      ),
    );
  }
}
