import 'dart:io';

import 'package:dynamic_theme/dynamic_theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:memeory/pages/home/home.dart';
import 'package:memeory/util/theme.dart';

void runMemeory() {
  HttpOverrides.global = CustomHttp();
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

class CustomHttp extends HttpOverrides {
  @override
  HttpClient createHttpClient(context) {
    return super.createHttpClient(context)
      ..badCertificateCallback = (_, __, ___) => true;
  }
}
