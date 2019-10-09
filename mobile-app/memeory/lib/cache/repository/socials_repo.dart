import 'dart:convert';

import 'package:memeory/model/user.dart';
import 'package:memeory/util/consts.dart';

import '../storage.dart';

Future<bool> isAuthorized() async {
  return (await getProfilesMap()).isNotEmpty;
}

Future<Map> getProfilesMap() async {
  var resultMap = {};
  [GOOGLE_PROVIDER, FACEBOOK_PROVIDER].forEach((p) async {
    var account = await getSocialsAccount(p);
    if (account.displayName != null) {
      resultMap[p] = account;
    }
  });
  return resultMap;
}

putProfilesMap(socialsMap) async {
  [GOOGLE_PROVIDER, FACEBOOK_PROVIDER].forEach((p) async {
    var map = socialsMap[p] ?? {};
    if (map.isNotEmpty) {
      await setSocialsAccount(p, ProviderAuth.fromJson(map));
    }
  });
}

Future<ProviderAuth> getSocialsAccount(String provider) async {
  var profile = await get(provider);
  var decodedProfile = profile != null ? json.decode(profile) : {};
  return ProviderAuth.fromJson(decodedProfile);
}

setSocialsAccount(String provider, ProviderAuth profile) async {
  await put(provider, json.encode(profile.toJson()));
}
