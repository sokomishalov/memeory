import 'dart:convert';
import 'dart:io';

import 'package:memeory/cache/repository/channels_repo.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/cache/repository/socials_repo.dart';
import 'package:memeory/cache/repository/theme_repo.dart';
import 'package:memeory/cache/repository/token_repo.dart';
import 'package:memeory/util/consts.dart';
import 'package:memeory/util/env.dart';
import 'package:memeory/util/http.dart';

Future saveProfile() async {
  final url = '${env.backendUrl}/profile/save';

  final body = json.encode({
    "id": await getToken(),
    "socialsMap": await getProfilesMap(),
    "selectedOrientation": (await getPreferredOrientation()).toString(),
    "watchAllChannels": await getWatchAll(),
    "channels": await getSelectedChannels(),
    "usesDarkTheme": await getUsesDarkTheme()
  });

  final headers = <String, String>{
    HttpHeaders.contentTypeHeader: APPLICATION_JSON_HEADER_VALUE,
  };

  final response = await http.post(url, body: body, headers: headers);

  var savedUser = json.decode(response.body);

  putToken(savedUser["id"]);

  return savedUser;
}
