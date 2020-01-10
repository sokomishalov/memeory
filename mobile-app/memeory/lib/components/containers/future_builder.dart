import 'package:flutter/material.dart';

import 'error.dart';
import 'loader.dart';

class FutureWidget<T> extends StatelessWidget {
  const FutureWidget({
    Key key,
    @required this.future,
    @required this.render(T data),
  }) : super(key: key);

  final Future<T> future;
  final Function(T) render;

  @override
  Widget build(BuildContext context) {
    return Container(
      child: FutureBuilder(
        future: future,
        builder: (_, snapshot) {
          switch (snapshot.connectionState) {
            case ConnectionState.none:
            case ConnectionState.waiting:
              return Loader();
            default:
              return !snapshot.hasError
                  ? render(snapshot.data)
                  : ErrorContainer(
                      error: snapshot?.error,
                      reload: () async => await future,
                    );
          }
        },
      ),
    );
  }
}
