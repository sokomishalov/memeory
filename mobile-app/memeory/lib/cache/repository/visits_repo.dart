import 'package:memeory/util/consts.dart';

import '../storage.dart';

Future<bool> isFirstAppVisit() async => await get(APP_VISIT_DATETIME_KEY) == null;
//Future<bool> isFirstAppVisit() async => true;

setAppVisitDatetime() async {
  final currentTimestamp = new DateTime.now().millisecondsSinceEpoch / 1000;
  return await put(APP_VISIT_DATETIME_KEY, currentTimestamp);
}
