import 'package:memeory/util/consts/consts.dart';

import '../storage.dart';

Future<String> getToken() async {
  return await get(TOKEN_KEY);
}

putToken(String token) async {
  await put(TOKEN_KEY, token);
}
