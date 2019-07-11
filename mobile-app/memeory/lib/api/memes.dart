import 'dart:convert';
import 'dart:io';

import 'package:memeory/cache/repository/token_repo.dart';
import 'package:memeory/util/consts.dart';
import 'package:memeory/util/env.dart';
import 'package:memeory/util/http.dart';

Future<List> fetchMemes(page) async {
  final headers = <String, String>{
    HttpHeaders.contentTypeHeader: 'application/json',
    MEMEORY_TOKEN_HEADER: await getToken()
  };

  final url = '${env.backendUrl}/memes/page/$page/$MEMES_COUNT_ON_THE_PAGE';
  final response = await http.get(url, headers: headers);
  return json.decode(utf8.decode(response.bodyBytes));
}
