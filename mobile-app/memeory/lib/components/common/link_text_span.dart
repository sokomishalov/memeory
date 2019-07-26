import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:memeory/components/containers/web_view.dart';

class LinkTextSpan extends TextSpan {
  LinkTextSpan({
    @required BuildContext context,
    @required String url,
    String text,
  }) : super(
          style: Theme.of(context)
              .textTheme
              .body2
              .copyWith(color: Theme.of(context).accentColor),
          text: text ?? url,
          recognizer: TapGestureRecognizer()
            ..onTap = () {
              openMemeoryWebView(context, url);
            },
        );
}
