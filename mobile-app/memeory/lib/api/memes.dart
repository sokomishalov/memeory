import 'dart:convert';
import 'dart:io';

import 'package:memeory/cache/repository/token_repo.dart';
import 'package:memeory/util/consts.dart';
import 'package:memeory/util/firebase.dart';
import 'package:memeory/util/http.dart';

Future<List> fetchMemes(page) async {
  final baseUrl = await getBackendUrl();
  final headers = <String, String>{
    HttpHeaders.contentTypeHeader: APPLICATION_JSON_HEADER_VALUE,
    MEMEORY_TOKEN_HEADER_NAME: await getToken()
  };

  final url = '${baseUrl}memes/page/$page/$MEMES_COUNT_ON_THE_PAGE';
  final response = await http.get(url, headers: headers);

  return json.decode(utf8.decode(response.bodyBytes));
}
