import 'dart:io';

import 'package:dynamic_theme/dynamic_theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:memeory/pages/home/home.dart';
import 'package:memeory/strings/ru.dart';
import 'package:memeory/theme/theme.dart';
import 'package:memeory/util/time.dart';

void runMemeory() {
  HttpOverrides.global = CustomHttp();
  initLocale();
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
          title: APP_NAME,
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
