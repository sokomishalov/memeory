import 'dart:convert';

import 'package:memeory/model/google_account.dart';
import 'package:memeory/util/consts.dart';

import '../storage.dart';

Future<Map> getProfilesMap() async {
  var resultMap = {};

  var google = await getGoogleProfile();
  if (google != null) {
    resultMap["GOOGLE"] = google;
  }

  var facebook = await getGoogleProfile();
  if (google != null) {
    resultMap["FACEBOOK"] = facebook;
  }

  return resultMap;
}

isAuthorized() async {
  var profile = await getGoogleProfile() ?? await getFacebookProfile();
  return profile != null;
}

getGoogleProfile() async {
  var profile = await get(GOOGLE_PROFILE_KEY);
  return profile != null ? GoogleAccount.fromJson(json.decode(profile)) : null;
}

setGoogleProfile(profile) async {
  await put(GOOGLE_PROFILE_KEY, json.encode(profile));
}

getFacebookProfile() async {
  var profile = await get(FACEBOOK_PROFILE_KEY);
  return profile != null ? json.decode(profile) : null;
}

setFacebookProfile(profile) async {
  await put(FACEBOOK_PROFILE_KEY, json.encode(profile));
}
