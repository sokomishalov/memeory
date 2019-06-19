import 'package:flutter/material.dart';
import 'package:memeory/common/buttons/rounded_button.dart';

class PreferencesPageWrapper extends StatelessWidget {
  PreferencesPageWrapper({
    this.title,
    this.nextPage,
    this.previousPage,
    this.close,
    @required this.child,
  });

  final String title;
  final VoidCallback nextPage;
  final VoidCallback previousPage;
  final VoidCallback close;
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
      header = Container();
    }

    if (nextPage != null) {
      bottomButton = RoundedButton(
        caption: "Далее",
        onPressed: nextPage,
      );
    } else if (close != null) {
      bottomButton = RoundedButton(
        caption: "Пора орать с мемесов!",
        onPressed: close,
      );
    } else {
      bottomButton = Container();
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
