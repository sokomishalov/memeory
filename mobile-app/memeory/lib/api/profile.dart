import 'dart:convert';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:memeory/cache/repository/channels_repo.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/cache/repository/socials_repo.dart';
import 'package:memeory/cache/repository/token_repo.dart';
import 'package:memeory/model/scrolling_axis.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/firebase/firebase.dart';
import 'package:memeory/util/http/http.dart';

Future<dynamic> fetchProfile() async {
  final baseUrl = await getBackendUrl();
  final headers = <String, String>{
    HttpHeaders.contentTypeHeader: APPLICATION_JSON_HEADER_VALUE,
    MEMEORY_TOKEN_HEADER_NAME: await getToken()
  };

  final url = '${baseUrl}profile/get';
  final response = await http.get(url, headers: headers);

  return json.decode(utf8.decode(response.bodyBytes));
}

Future<dynamic> saveSocialsAccounts(List accounts) async {
  final baseUrl = await getBackendUrl();
  final url = '${baseUrl}profile/socials/add';

  final socials = await getSocialsMap();
  final body = json.encode(socials);

  final headers = <String, String>{
    HttpHeaders.contentTypeHeader: APPLICATION_JSON_HEADER_VALUE,
  };

  final response = await http.post(url, body: body, headers: headers);

  return json.decode(utf8.decode(response.bodyBytes));
}

Future<dynamic> saveProfile() async {
  final baseUrl = await getBackendUrl();
  final url = '${baseUrl}profile/save';

  final body = json.encode({
    "id": await getToken(),
    "selectedOrientation": describeEnum(await getPreferredOrientation()),
    "watchAllChannels": await getWatchAll(),
    "channels": await getSelectedChannels()
  });

  final headers = <String, String>{
    HttpHeaders.contentTypeHeader: APPLICATION_JSON_HEADER_VALUE,
  };

  final response = await http.post(url, body: body, headers: headers);
  var savedUser = json.decode(utf8.decode(response.bodyBytes));

  await putToken(
    savedUser["id"],
  );
  await setPreferredOrientation(
    orientationFromString(savedUser["selectedOrientation"].toString()),
  );
  await setWatchAll(
    savedUser["watchAllChannels"],
  );
  await setChannels(
    savedUser["channels"],
  );

  return savedUser;
}
