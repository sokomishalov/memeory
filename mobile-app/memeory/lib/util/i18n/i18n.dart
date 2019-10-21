import 'package:flutter/cupertino.dart';
import 'package:flutter_i18n/flutter_i18n.dart';
import 'package:flutter_i18n/flutter_i18n_delegate.dart';

String t(BuildContext context, String key) {
  return FlutterI18n.translate(context, key);
}

FlutterI18nDelegate initLocale() {
  final FlutterI18nDelegate flutterI18nDelegate = FlutterI18nDelegate(
    useCountryCode: false,
    fallbackFile: 'en',
    path: 'assets/i18n',
  );
  flutterI18nDelegate.load(null).then((_) {
    debugPrint("loaded translations");
  });
  return flutterI18nDelegate;
}
