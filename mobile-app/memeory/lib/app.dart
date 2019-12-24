import 'dart:io';

import 'package:dynamic_theme/dynamic_theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_i18n/flutter_i18n_delegate.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:memeory/pages/initial/initial.dart';
import 'package:memeory/pages/memes/memes.dart';
import 'package:memeory/pages/preferences/preferences.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:memeory/util/theme/theme.dart';

runMemeory() {
  HttpOverrides.global = CustomHttp();
  var i18nDelegate = initLocale();
  runApp(DmsApp(i18nDelegate));
}

class DmsApp extends StatelessWidget {
  final FlutterI18nDelegate i18nDelegate;

  DmsApp(this.i18nDelegate);

  @override
  Widget build(BuildContext context) {
    return DynamicTheme(
      defaultBrightness: Brightness.dark,
      data: themeBuilder,
      themedWidgetBuilder: (context, theme) {
        return MaterialApp(
          theme: theme,
          title: APP_NAME,
          initialRoute: ROUTES.INITIAL.route,
          routes: {
            ROUTES.INITIAL.route: (_) => InitialPage(),
            ROUTES.PREFERENCES.route: (_) => UserPreferencesPage(),
            ROUTES.MEMES.route: (_) => MemesPage(),
          },
          localizationsDelegates: [
            i18nDelegate,
            GlobalMaterialLocalizations.delegate,
            GlobalWidgetsLocalizations.delegate
          ],
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
