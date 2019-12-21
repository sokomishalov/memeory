import 'package:flutter/material.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/theme/dark.dart';
import 'package:memeory/util/theme/light.dart';
import 'package:memeory/util/theme/theme.dart';

class CustomAppBar extends StatefulWidget {
  @override
  _CustomAppBarState createState() => _CustomAppBarState();
}

class _CustomAppBarState extends State<CustomAppBar> {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      backgroundColor: dependingOnThemeChoice(
        context: context,
        light: APP_BAR_COLOR_LIGHT,
        dark: APP_BAR_COLOR_DARK,
      ),
      iconTheme: getDefaultIconThemeData(context),
      centerTitle: true,
      title: Text(
        t(context, "memes"),
        style: TextStyle(
          color: dependingOnThemeChoice(
            context: context,
            light: TEXT_COLOR_LIGHT,
            dark: TEXT_COLOR_DARK,
          ),
        ),
      ),
    );
  }
}