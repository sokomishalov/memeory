import 'package:flutter/material.dart';
import 'package:memeory/util/env/env.dart';

class DebugWidget extends StatelessWidget {
  const DebugWidget({Key key, this.child}) : super(key: key);

  final Widget child;

  @override
  Widget build(BuildContext context) {
    return isDebugModeOn() ? child : Container();
  }
}
