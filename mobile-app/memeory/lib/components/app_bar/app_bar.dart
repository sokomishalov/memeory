import 'package:flutter/material.dart';
import 'package:memeory/theme/dark.dart';
import 'package:memeory/theme/light.dart';
import 'package:memeory/theme/theme.dart';

class DefaultAppBar extends StatelessWidget {
  const DefaultAppBar({Key key, this.text}) : super(key: key);

  final String text;

  @override
  Widget build(BuildContext context) {
    return Container(
      child: AppBar(
        backgroundColor: dependingOnThemeChoice(
          context: context,
          light: APP_BAR_COLOR_LIGHT,
          dark: APP_BAR_COLOR_DARK,
        ),
        iconTheme: getDefaultIconThemeData(context),
        centerTitle: true,
        title: Text(
          text,
          style: TextStyle(
            color: dependingOnThemeChoice(
              context: context,
              light: TEXT_COLOR_LIGHT,
              dark: TEXT_COLOR_DARK,
            ),
          ),
        ),
      ),
    );
  }
}

PreferredSize buildAppBar(String text) {
  return PreferredSize(
    preferredSize: Size.fromHeight(50),
    child: DefaultAppBar(text: text),
  );
}
