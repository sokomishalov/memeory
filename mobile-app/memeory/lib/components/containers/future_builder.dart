import 'package:flutter/material.dart';

import 'error.dart';
import 'loader.dart';

class FutureWidget extends StatelessWidget {
  const FutureWidget({
    Key key,
    @required this.future,
    @required this.render(dynamic data),
  }) : super(key: key);

  final Future future;
  final Function render;

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
