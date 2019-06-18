import 'package:dynamic_theme/dynamic_theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:memeory/pages/home/home.dart';
import 'package:memeory/util/theme.dart';

void runMemeory() {
  runApp(DmsApp());
}

class DmsApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DynamicTheme(
      defaultBrightness: Brightness.dark,
      data: themeBuilder,
      themedWidgetBuilder: (context, theme) {
        return MaterialApp(
          theme: theme,
          title: "Memeory",
          home: HomePage(),
        );
      },
    );
  }
}
