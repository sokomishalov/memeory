import 'dart:convert';

import 'package:memeory/model/user.dart';
import 'package:memeory/util/consts/consts.dart';

import '../storage.dart';

Future<bool> isAuthorized() async {
  return (await getSocialsMap()).isNotEmpty;
}

Future<Map> getSocialsMap() async {
  var resultMap = {};
  [GOOGLE_PROVIDER, FACEBOOK_PROVIDER].forEach((p) async {
    var account = await getSocialsAccount(p);
    if (account.displayName != null) {
      resultMap[p] = account;
    }
  });
  return resultMap;
}

Future<ProviderAuth> getSocialsAccount(String provider) async {
  var profile = await get(provider);
  var decodedProfile = profile != null ? json.decode(profile) : <String, dynamic>{};
  return ProviderAuth.fromJson(decodedProfile);
}

putSocialsAccount(String provider, ProviderAuth profile) async {
  await put(provider, json.encode(profile.toJson()));
}
