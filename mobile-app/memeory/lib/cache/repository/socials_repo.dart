import 'dart:convert';

import 'package:memeory/model/google_account.dart';
import 'package:memeory/util/consts.dart';

import '../storage.dart';

getGoogleProfile() async {
  return GoogleAccount.fromJson(json.decode(await get(GOOGLE_PROFILE_KEY)));
}

setGoogleProfile(profile) async {
  await put(GOOGLE_PROFILE_KEY, json.encode(profile));
}
