import 'package:flutter/material.dart';
import 'package:flutter_i18n/flutter_i18n.dart';
import 'package:timeago/timeago.dart' as timeago;

String timeAgo(BuildContext context, String at) {
  return timeago.format(
    DateTime.parse(at),
    locale: FlutterI18n.currentLocale(context)?.languageCode,
  );
}
