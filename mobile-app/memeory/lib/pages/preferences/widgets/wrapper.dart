import 'package:flutter/material.dart';
import 'package:memeory/components/buttons/rounded_button.dart';
import 'package:memeory/components/containers/empty.dart';
import 'package:memeory/strings/ru.dart';

class PreferencesPageWrapper extends StatelessWidget {
  PreferencesPageWrapper({
    this.title,
    this.nextPage,
    this.apply,
    this.applyText,
    @required this.child,
  });

  final String title;
  final VoidCallback nextPage;
  final VoidCallback apply;
  final String applyText;
  final Widget child;

  @override
  Widget build(BuildContext context) {
    Widget bottomButton;
    Widget header;

    if (title != null) {
      header = Container(
        padding: EdgeInsets.only(top: 10, bottom: 10),
        child: Text(title),
      );
    } else {
      header = Empty();
    }

    if (nextPage != null) {
      bottomButton = RoundedButton(
        caption: NEXT,
        onPressed: nextPage,
      );
    } else if (apply != null) {
      bottomButton = RoundedButton(
        caption: applyText ?? SAVE_CHANGES,
        onPressed: apply,
      );
    } else {
      bottomButton = Empty();
    }

    return Container(
      padding: EdgeInsets.symmetric(vertical: 40),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          Container(
            padding: EdgeInsets.symmetric(horizontal: 20),
            child: header,
          ),
          child,
          Container(
            padding: EdgeInsets.symmetric(horizontal: 20),
            child: bottomButton,
          ),
        ],
      ),
    );
  }
}
