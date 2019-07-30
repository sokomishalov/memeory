import 'package:flutter/material.dart';
import 'package:memeory/components/containers/empty.dart';

class ConditionalWidget extends StatelessWidget {
  const ConditionalWidget({
    Key key,
    this.condition,
    this.child,
  }) : super(key: key);

  final bool condition;
  final Widget child;

  @override
  Widget build(BuildContext context) {
    return condition ? child : Empty();
  }
}
