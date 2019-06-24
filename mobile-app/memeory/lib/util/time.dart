import 'package:timeago/timeago.dart' as timeago;

initLocale() {
  timeago.setLocaleMessages('ru', timeago.RuMessages());
}

timeAgo(String at) => timeago.format(DateTime.parse(at), locale: 'ru');
