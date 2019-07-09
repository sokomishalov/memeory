import 'package:memeory/util/consts.dart';

import '../storage.dart';

Future<bool> getUsesDarkTheme() async {
  return await get(THEME_KEY) != false.toString();
}

setUsesDarkTheme(bool value) async {
  await put(THEME_KEY, value);
}
