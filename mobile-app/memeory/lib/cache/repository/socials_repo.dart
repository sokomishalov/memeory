import 'dart:convert';

import 'package:memeory/util/consts.dart';

import '../storage.dart';

Future<Map> getProfilesMap() async {
  var resultMap = {};

  var google = await getGoogleProfile();
  if (google != null) {
    resultMap[GOOGLE_PROVIDER] = google;
  }

  var facebook = await getFacebookProfile();
  if (facebook != null) {
    resultMap[FACEBOOK_PROVIDER] = facebook;
  }

  return resultMap;
}

Future<bool> isAuthorized() async {
  var profile = await getGoogleProfile() ?? await getFacebookProfile();
  return profile != null;
}

Future<dynamic> getGoogleProfile() async {
  var profile = await get(GOOGLE_PROFILE_KEY);
  return profile != null ? json.decode(profile) : null;
}

Future<dynamic> getFacebookProfile() async {
  var profile = await get(FACEBOOK_PROFILE_KEY);
  return profile != null ? json.decode(profile) : null;
}

putProfilesMap(socialsMap) async {
  await setGoogleProfile(socialsMap[GOOGLE_PROVIDER]);
  await setFacebookProfile(socialsMap[FACEBOOK_PROVIDER]);
}

setGoogleProfile(profile) async {
  await put(GOOGLE_PROFILE_KEY, json.encode(profile));
}

setFacebookProfile(profile) async {
  await put(FACEBOOK_PROFILE_KEY, json.encode(profile));
}
