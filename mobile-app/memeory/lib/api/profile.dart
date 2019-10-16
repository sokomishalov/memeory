import 'dart:convert';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:memeory/cache/repository/channels_repo.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/cache/repository/socials_repo.dart';
import 'package:memeory/cache/repository/token_repo.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/util/consts.dart';
import 'package:memeory/util/firebase.dart';
import 'package:memeory/util/http.dart';

Future<dynamic> saveProfile() async {
  final baseUrl = await getBackendUrl();
  final url = '${baseUrl}profile/save';

  final body = json.encode({
    "id": await getToken(),
    "socialsMap": await getProfilesMap(),
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
  await putProfilesMap(
    savedUser["socialsMap"],
  );

  return savedUser;
}
